package com.edulexa.activity.staff.student_profile.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.k12_diary.activity.K12DiaryActivity
import com.edulexa.activity.staff.student_profile.adapter.StudentListAdapter
import com.edulexa.activity.staff.student_profile.model.student_list.StudentListResponse
import com.edulexa.activity.staff.student_profile.model.student_profile_detail.StudentViewResponse
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityStudentListStaffBinding
import com.edulexa.databinding.ActivityStudentProfileDetailStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentProfileDetailACtivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityStudentProfileDetailStaffBinding? = null
    var studentId = ""
    var tokenStr = ""
    var userIdStr = ""
    var classIdStr = ""
    var sectionIdStr = ""
    var studentSessionIdStr = ""
    var studentNameStr = ""
    var classNameStr = ""
    var sectionNameStr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileDetailStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpStudentProfileData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.cvK12.setOnClickListener(this)
        binding!!.cvHomework.setOnClickListener(this)
        binding!!.cvSchoolFee.setOnClickListener(this)
        binding!!.cvLiveClassAttendance.setOnClickListener(this)
        binding!!.cvOnlineExam.setOnClickListener(this)
        binding!!.cvReportCard.setOnClickListener(this)
        binding!!.cvSharedLesson.setOnClickListener(this)
        binding!!.cvProfile.setOnClickListener(this)
        binding!!.cvLoginAsStudent.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            studentId = bundle!!.getString(Constants.StaffStudentProfile.STUDENT_ID)!!
            val studentImage = bundle.getString(Constants.StaffStudentProfile.IMAGE)!!
            val studentName = bundle.getString(Constants.StaffStudentProfile.NAME)!!
            val studentRollNo = bundle.getString(Constants.StaffStudentProfile.ROLL_NO)!!
            val studentClass = bundle.getString(Constants.StaffStudentProfile.CLASS)!!
            val studentSection = bundle.getString(Constants.StaffStudentProfile.SECTION)!!

            Utils.setpProfileImageUsingGlide(
                mActivity!!,
                Constants.BASE_URL_STAFF + studentImage,
                binding!!.ivImage
            )
            binding!!.tvName.text = studentName
            binding!!.tvClassSection.text = getString(
                R.string.concat_string_with_text_format,
                studentClass,
                "-",
                studentSection
            )
            binding!!.tvRollNo.text =
                getString(R.string.library_student_string_format, "Roll No : ", studentRollNo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpStudentProfileData() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val preference = Preference().getInstance(mActivity!!)
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
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID,  Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.STUDENT_ID, studentId)
            builder.addFormDataPart(Constants.ParamsStaff.ROLE_ID,  Utils.getStaffRoleId(mActivity!!))
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "view_student_profile")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getStudentProfile(requestBody)
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
                            if (status == 1) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    StudentViewResponse::class.java
                                ) as StudentViewResponse
                                if (modelResponse.getData() != null) {
                                    tokenStr =
                                        modelResponse.getData()!!.getTokenData()!!.getToken()!!
                                    userIdStr =
                                        modelResponse.getData()!!.getTokenData()!!.getUsersId()!!
                                    studentId = modelResponse.getData()!!.getStudent()!!.getId()!!
                                    classIdStr =
                                        modelResponse.getData()!!.getStudent()!!.getClassId()!!
                                    sectionIdStr =
                                        modelResponse.getData()!!.getStudent()!!.getSectionId()!!
                                    studentSessionIdStr = modelResponse.getData()!!.getStudent()!!
                                        .getStudentSessionId()!!
                                    studentNameStr = modelResponse.getData()!!.getStudent()!!
                                        .getFirstname()!! + modelResponse.getData()!!.getStudent()!!
                                        .getLastname()
                                    classNameStr =
                                        modelResponse.getData()!!.getStudent()!!.getClass_()!!
                                    sectionNameStr =
                                        modelResponse.getData()!!.getStudent()!!.getSection()!!
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
        else if (id == R.id.cv_k12) {
            val bundle = Bundle()
            bundle.putString(Constants.StaffK12Timeline.STUDENT_ID, studentId)
            startActivity(Intent(mActivity, K12DiaryActivity::class.java).putExtras(bundle))
        } else if (id == R.id.cv_homework) {

        } else if (id == R.id.cv_school_fee) {

        } else if (id == R.id.cv_live_class_attendance) {

        } else if (id == R.id.cv_online_exam) {

        } else if (id == R.id.cv_report_card) {

        } else if (id == R.id.cv_shared_lesson) {

        } else if (id == R.id.cv_profile) {

        } else if (id == R.id.cv_login_as_student) {

        }
    }
}