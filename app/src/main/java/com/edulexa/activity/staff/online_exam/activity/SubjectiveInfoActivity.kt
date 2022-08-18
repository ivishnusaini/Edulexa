package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.adapter.exam_wise.ExamwiseQuestionsAdapter
import com.edulexa.activity.staff.online_exam.adapter.subjective.SubjectiveInfoAdapter
import com.edulexa.activity.staff.online_exam.model.examwise_questions.ExamwiseQuestionsResponse
import com.edulexa.activity.staff.online_exam.model.subjective.info.SubjectiveInfoResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityExamWiseQuestionsStaffBinding
import com.edulexa.databinding.ActivitySubjectiveInfoStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectiveInfoActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySubjectiveInfoStaffBinding? = null
    var preference: Preference? = null

    var examId = ""
    var onlineExamStudentId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectiveInfoStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        getQuestions()
        Utils.showProgressBar(mActivity!!)
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getBundleData(){
        try {
            val bundle = intent.extras
            examId = bundle!!.getString(Constants.StaffOnlineExam.EXAM_ID)!!
            onlineExamStudentId = bundle.getString(Constants.StaffOnlineExam.ONLINEEXAM_STUDENT_ID)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getQuestions(){
        if (Utils.isNetworkAvailable(mActivity!!)) {
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
            builder.addFormDataPart(Constants.ParamsStaff.ONLINEEXAM_STUDENT_ID, onlineExamStudentId)
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getStudentSubjectiveExam")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getStudentSubjectiveExam(requestBody)
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
                            val questionResultsJsonArr = responseJsonObject.optJSONArray("question_result")
                            if (questionResultsJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SubjectiveInfoResponse::class.java
                                ) as SubjectiveInfoResponse
                                if (modelResponse.getQuestionResult()!!.isNotEmpty()) {
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.GONE
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity,
                                        LinearLayoutManager.VERTICAL,false)
                                    binding!!.recyclerView.adapter = SubjectiveInfoAdapter(mActivity!!,modelResponse.getQuestionResult())
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