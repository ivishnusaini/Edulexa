package com.edulexa.activity.staff.online_exam.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.ClassSpinnerAdapter
import com.edulexa.activity.staff.custom_lesson_plan.adapter.MultiSectionSelectAdapter
import com.edulexa.activity.staff.custom_lesson_plan.model.add_lesson.DatumAddLesson
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.activity.staff.student_profile.model.section.SectionResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.DialogStaffSelectMultipleSectionBinding
import com.edulexa.databinding.FragmentPracticeExamStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PracticeExamFragment : Fragment(), View.OnClickListener {
    var binding: FragmentPracticeExamStaffBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    var classListSpinn: List<ClassData?>? = ArrayList()
    var classSpinnerAdapter: ClassSpinnerAdapter? = null
    var classId = ""
    var sectionListSpinn: List<Section?>? = ArrayList()
    var sectionId = ""
    var sectionName = ""

    var preference: Preference? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: PracticeExamFragment? = null
        fun newInstance(): PracticeExamFragment? {
            fragment = PracticeExamFragment()
            return fragment
        }

        fun getInstance(): PracticeExamFragment? {
            return if (fragment == null) PracticeExamFragment() else fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPracticeExamStaffBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    private fun init() {
        mActivity = activity
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getClassList()
    }

    private fun setUpClickListener() {
        binding!!.sectionLay.setOnClickListener(this)
        binding!!.btnCreateAddQuestion.setOnClickListener(this)

        binding!!.classSpinn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (classListSpinn!![position]!!.getId() != null) {
                    classId = classListSpinn!![position]!!.getId()!!
                    getSection()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getClassList() {
        (classListSpinn as ArrayList<ClassData?>).clear()
        if (classSpinnerAdapter != null)
            classSpinnerAdapter!!.notifyDataSetChanged()
        classId = ""
        (sectionListSpinn as ArrayList<Section?>).clear()
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!),
                    dbId!!
                ).create(ApiInterfaceStaff::class.java)


            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            val requestBody = builder.build()


            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getClasses")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getClasses(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val responseJsonObject = JSONObject(responseStr)
                            val status = responseJsonObject.optInt("status")
                            if (status == 200) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    ClassResponse::class.java
                                ) as ClassResponse
                                if (modelResponse.getClasses() != null && modelResponse.getClasses()!!
                                        .isNotEmpty()
                                ) {
                                    val classData = ClassData()
                                    classData.setClass_("Select class")
                                    classListSpinn = modelResponse.getClasses()
                                    (classListSpinn as ArrayList<ClassData?>).add(0, classData)
                                    classSpinnerAdapter =
                                        ClassSpinnerAdapter(mActivity!!, classListSpinn)
                                    binding!!.classSpinn.adapter = classSpinnerAdapter
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
    }

    private fun getSection() {
        (sectionListSpinn as ArrayList<Section?>).clear()
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!),
                    dbId!!
                ).create(ApiInterfaceStaff::class.java)

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getClassSections")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getClassSections(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val responseJsonObject = JSONObject(responseStr)
                            val sectionListJsonArr = responseJsonObject.optJSONArray("section_list")
                            if (sectionListJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SectionResponse::class.java
                                ) as SectionResponse
                                if (modelResponse.getSectionList() != null && modelResponse.getSectionList()!!
                                        .isNotEmpty()
                                ) {
                                    sectionListSpinn = modelResponse.getSectionList()
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun setSectionSpinnerAdapter() {
        try {
            val dialog = Dialog(mActivity!!)
            var dialogBinding: DialogStaffSelectMultipleSectionBinding? = null
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding = DialogStaffSelectMultipleSectionBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)

            dialogBinding.multiSectionRecycler.layoutManager =
                LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            val multiSectionAdapter = MultiSectionSelectAdapter(mActivity!!, sectionListSpinn)
            dialogBinding.multiSectionRecycler.adapter = multiSectionAdapter

            dialogBinding.btnCancel.setOnClickListener {
                sectionName = ""
                sectionId = ""
                binding!!.tvSection.text = getString(R.string.add_custom_lesson_plan_staff_section)
                dialog.dismiss()
                dialogBinding.multiSectionRecycler.adapter = multiSectionAdapter
            }

            dialogBinding.btnSelect.setOnClickListener {
                sectionName = ""
                sectionId = ""
                for (i in sectionListSpinn!!.indices) {
                    if (sectionListSpinn!![i]!!.isSectionSelect()) {
                        val name = sectionListSpinn!![i]!!.getSection()
                        if (sectionName == "") {
                            sectionName = name!!
                            sectionId = sectionListSpinn!![i]!!.getId()!!
                        } else {
                            sectionName = sectionName + "," + name
                            sectionId =
                                sectionId + "," + sectionListSpinn!![i]!!.getId()
                        }
                    }
                }

                binding!!.tvSection.text = sectionName
                dialog.dismiss()
            }

            if (dialog.isShowing) dialog.dismiss()
            dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun addPracticeExam(){
        val titleStr = binding!!.etExamTitle.text.toString().trim()
        val durationStr = binding!!.etExamTitle.text.toString().trim()
        val timeInMinuteStr = binding!!.etExamDuration.text.toString().trim()
        val descriptionStr = binding!!.etExamDescription.text.toString().trim()
        if (titleStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_title_validation))
        else if (durationStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_duration_validation))
        else if (classId.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_class_validation))
        else if (sectionId.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_section_validation))
        else if (timeInMinuteStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_description_validation))
        else{
            if (Utils.isNetworkAvailable(mActivity!!)) {
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)

                val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

                val apiInterfaceWithHeader: ApiInterfaceStaff =
                    APIClientStaff.getRetroFitClientWithNewKeyHeader(
                        mActivity!!,
                        Utils.getStaffToken(mActivity!!),
                        Utils.getStaffId(mActivity!!),
                        dbId!!
                    ).create(ApiInterfaceStaff::class.java)


                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(Constants.ParamsStaff.EXAM, titleStr)
                builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
                builder.addFormDataPart(Constants.ParamsStaff.IS_ACTIVE, "1")
                builder.addFormDataPart(Constants.ParamsStaff.PUBLISH_RESULT, "0")
                builder.addFormDataPart(Constants.ParamsStaff.DURATION, durationStr)
                builder.addFormDataPart(Constants.ParamsStaff.DESCRIPTION, descriptionStr)
                builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
                builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, sectionId)
                val requestBody = builder.build()


                Utils.printLog("Url", Constants.BASE_URL_STAFF + "createPracticeExam")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.createPracticeExam(requestBody)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Utils.hideProgressBar()
                        try {
                            val responseStr = response.body()!!.string()
                            if (!responseStr.isNullOrEmpty()) {
                                val responseJsonObject = JSONObject(responseStr)
                                val message = responseJsonObject.optString("message")
                                Utils.showToast(mActivity!!, message)
                            } else {
                                Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.response_null_or_empty_validation)
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Utils.hideProgressBar()
                        Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    }

                })
            } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

        }
    }


    override fun onClick(requireView: View?) {
        val id = requireView!!.id
        if (id == R.id.section_lay) {
            if (classId != "") {
                sectionId = ""
                setSectionSpinnerAdapter()
            }else Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_select_class_first))
        }else if (id == R.id.btn_create_add_question)
            addPracticeExam()
    }
}