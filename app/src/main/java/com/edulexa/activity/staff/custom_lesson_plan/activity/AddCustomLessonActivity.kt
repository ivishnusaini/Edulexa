package com.edulexa.activity.staff.custom_lesson_plan.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.ClassListCustomLessonAdapter
import com.edulexa.activity.staff.custom_lesson_plan.adapter.ClassSpinnerAdapter
import com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list.FormFields
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddCustomLessonStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddCustomLessonActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddCustomLessonStaffBinding? = null

    var selectedClassId = ""
    var selectedSectionId = ""
    var selectedSectionName = ""
    var selectedSubjectId = ""
    var idStr = ""
    var fromFieldStr = ""

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var preference: Preference? = null

    var classListSpinn: List<ClassData?>? = ArrayList()
    var classSpinnerAdapter : ClassSpinnerAdapter? = null
    var classId = ""
    var sectionListSpinn: List<Section?>? = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomLessonStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        getFormFieldData()
        setUpFromDate()
        getClassList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.uploadDateLay.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            selectedClassId = bundle!!.getString(Constants.StaffCustomLessonPlan.CLASS_ID)!!
            selectedSectionId = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_ID)!!
            selectedSectionName = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_NAME)!!
            selectedSubjectId = bundle.getString(Constants.StaffCustomLessonPlan.SUBJECT_ID)!!
            idStr = bundle.getString(Constants.StaffCustomLessonPlan.ID)!!
            fromFieldStr = bundle.getString(Constants.StaffCustomLessonPlan.FORM_FIELD)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFormFieldData() {
        try {
            if (fromFieldStr != "") {
                val formFieldModel =
                    Utils.getObject(fromFieldStr, FormFields::class.java) as FormFields
                if (formFieldModel.getIsLesson()
                        .equals("1")
                ) binding!!.lessonLay.visibility =
                    View.VISIBLE else binding!!.lessonLay.visibility =
                    View.GONE
                if (formFieldModel.getIsPreviousKnowledge()
                        .equals("1")
                ) binding!!.previousKnowledgeLay.visibility =
                    View.VISIBLE else binding!!.previousKnowledgeLay.visibility =
                    View.GONE
                if (formFieldModel.getIsComprehensiveQuestions()
                        .equals("1")
                ) binding!!.comprehensiveQuestionsLay.visibility =
                    View.VISIBLE else binding!!.comprehensiveQuestionsLay.visibility =
                    View.GONE
                if (formFieldModel.getIsLectureYoutubeUrl()
                        .equals("1")
                ) binding!!.lectureYoutubeLay.visibility =
                    View.VISIBLE else binding!!.lectureYoutubeLay.visibility =
                    View.GONE
                if (formFieldModel.getIsTopic()
                        .equals("1")
                ) binding!!.topicLay.visibility = View.VISIBLE else binding!!.topicLay.visibility =
                    View.GONE
                if (formFieldModel.getIsTeachingMethod()
                        .equals("1")
                ) binding!!.teachingMethodLay.visibility =
                    View.VISIBLE else binding!!.teachingMethodLay.visibility =
                    View.GONE
                if (formFieldModel.getIsGeneralObjectives()
                        .equals("1")
                ) binding!!.generalObjectiveLay.visibility =
                    View.VISIBLE else binding!!.generalObjectiveLay.visibility =
                    View.GONE
                if (formFieldModel.getPeriod().equals("1")) binding!!.periodLay.visibility =
                    View.VISIBLE else binding!!.periodLay.visibility =
                    View.GONE
                if (formFieldModel.getTeachingAids()
                        .equals("1")
                ) binding!!.teachingAidsLay.visibility =
                    View.VISIBLE else binding!!.teachingAidsLay.visibility = View.GONE
                if (formFieldModel.getPortionActuallyTaught()
                        .equals("1")
                ) binding!!.portionActuallyTaughtLay.visibility =
                    View.VISIBLE else binding!!.portionActuallyTaughtLay.visibility =
                    View.GONE
                if (formFieldModel.getHwAssigned()
                        .equals("1")
                ) binding!!.homeworkAssignedLay.visibility =
                    View.VISIBLE else binding!!.homeworkAssignedLay.visibility = View.GONE
                if (formFieldModel.getHwNotAssignedReason()
                        .equals("1")
                ) binding!!.homeworkNotAssignedReasonLay.visibility =
                    View.VISIBLE else binding!!.homeworkNotAssignedReasonLay.visibility =
                    View.GONE
            } else {
                binding!!.lessonLay.visibility = View.GONE
                binding!!.comprehensiveQuestionsLay.visibility = View.GONE
                binding!!.generalObjectiveLay.visibility = View.GONE
                binding!!.lectureYoutubeLay.visibility = View.GONE
                binding!!.teachingMethodLay.visibility = View.GONE
                binding!!.previousKnowledgeLay.visibility = View.GONE
                binding!!.topicLay.visibility = View.GONE
                binding!!.periodLay.visibility = View.GONE
                binding!!.teachingAidsLay.visibility = View.GONE
                binding!!.portionActuallyTaughtLay.visibility = View.GONE
                binding!!.homeworkAssignedLay.visibility = View.GONE
                binding!!.homeworkNotAssignedReasonLay.visibility = View.GONE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpFromDate() {
        myCalendar = Calendar.getInstance()
        fromDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData()
            }
    }

    private fun updateLabelFromData() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding!!.tvUploadDate.text = sdf.format(myCalendar!!.time)
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
                                    (classListSpinn as ArrayList<ClassData?>).add(0,classData)
                                    classSpinnerAdapter = ClassSpinnerAdapter(mActivity!!,classListSpinn)
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


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.upload_date_lay) {
            binding!!.tvUploadDate.text = getString(R.string.custom_lesson_plan_staff_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                fromDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }
    }
}