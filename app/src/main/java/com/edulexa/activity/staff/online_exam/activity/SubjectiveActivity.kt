package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.Scroller
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.ClassSpinnerAdapter
import com.edulexa.activity.staff.custom_lesson_plan.model.add_lesson.DatumAddLesson
import com.edulexa.activity.staff.online_exam.adapter.SectionSpinnerAdapter
import com.edulexa.activity.staff.online_exam.adapter.exam_wise.ExamwiseQuestionsAdapter
import com.edulexa.activity.staff.online_exam.adapter.subjective.SubjectiveAdapter
import com.edulexa.activity.staff.online_exam.model.examwise_questions.ExamwiseQuestionsResponse
import com.edulexa.activity.staff.online_exam.model.subjective.list.SubjectiveListResponse
import com.edulexa.activity.staff.student_profile.adapter.SectionAdapter
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.activity.staff.student_profile.model.section.SectionResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddCustomLessonStaffBinding
import com.edulexa.databinding.ActivitySubjectiveStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SubjectiveActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySubjectiveStaffBinding? = null
    var preference: Preference? = null
    var classListSpinn: List<ClassData?>? = ArrayList()
    var classSpinnerAdapter: ClassSpinnerAdapter? = null
    var classId = ""
    var sectionListSpinn: List<Section?>? = ArrayList()
    var sectionId = ""

    var examId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectiveStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        getClassList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.classSpinn.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (classListSpinn!![position]!!.getId() != "") {
                    classId = classListSpinn!![position]!!.getId()!!
                    getSection()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding!!.sectionSpinn.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (sectionListSpinn!![position]!!.getId() != "") {
                        sectionId = sectionListSpinn!![position]!!.getId()!!
                        getStudentList()
                    } else sectionId = ""
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            examId = bundle!!.getString(Constants.StaffOnlineExam.EXAM_ID)!!
        } catch (e: Exception) {
            e.printStackTrace()
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
                                    val section = Section()
                                    section.setSection("Select section")
                                    sectionListSpinn = modelResponse.getSectionList()
                                    (sectionListSpinn as ArrayList<Section?>).add(0, section)
                                    binding!!.sectionSpinn.adapter = SectionSpinnerAdapter(mActivity!!, sectionListSpinn)
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

    private fun getStudentList(){
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)
            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!), dbId!!
                ).create(ApiInterfaceStaff::class.java)

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.EXAM_ID, examId)
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
            builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, sectionId)
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "subjectiveMarkReport")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.subjectiveMarkReport(requestBody)
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
                            val resultsJsonArr = responseJsonObject.optJSONArray("results")
                            if (resultsJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SubjectiveListResponse::class.java
                                ) as SubjectiveListResponse
                                if (modelResponse.getResults()!!.isNotEmpty()) {
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity,
                                        LinearLayoutManager.VERTICAL,false)
                                    binding!!.recyclerView.adapter = SubjectiveAdapter(mActivity!!,modelResponse.getResults(),examId)
                                } else {
                                    binding!!.recyclerView.visibility = View.GONE
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                                binding!!.recyclerView.visibility = View.GONE
                                binding!!.tvNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.recyclerView.visibility = View.GONE
                            binding!!.tvNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.recyclerView.visibility = View.GONE
                        binding!!.tvNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }

}