package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.adapter.exam_wise.ExamwiseQuestionsAdapter
import com.edulexa.activity.staff.online_exam.model.examwise_questions.ExamwiseQuestionsResponse
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.activity.staff.online_exam.model.list.OnlineExamListResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddExamStaffBinding
import com.edulexa.databinding.ActivityExamWiseQuestionsStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ExamWiseQuestionsActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityExamWiseQuestionsStaffBinding? = null
    var preference: Preference? = null

    var examId = ""
    var examType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamWiseQuestionsStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        getQuestionsList()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        Utils.showProgressBar(mActivity!!)
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.addQuestionLay.setOnClickListener(this)
    }

    private fun getBundleData(){
        try {
            val bundle = intent.extras
            examId = bundle!!.getString(Constants.StaffOnlineExam.EXAM_ID)!!
            val examTypeStr = bundle.getString(Constants.StaffOnlineExam.EXAM_TYPE)!!
            examType = if (examTypeStr == "0")
                "Online"
            else "Practice"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getQuestionsList(){
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
            builder.addFormDataPart(
                Constants.ParamsStaff.ROLE_ID,
                Utils.getStaffRoleId(mActivity!!)
            )
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getByOnlineExamQuestion")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getByOnlineExamQuestion(requestBody)
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
                            val questionsJsonArr = responseJsonObject.optJSONArray("questions")
                            if (questionsJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    ExamwiseQuestionsResponse::class.java
                                ) as ExamwiseQuestionsResponse
                                if (modelResponse.getQuestions()!!.isNotEmpty()) {
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false)
                                    binding!!.recyclerView.adapter = ExamwiseQuestionsAdapter(mActivity!!,modelResponse.getQuestions(),examId,examType)
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
        else if (id == R.id.add_question_lay){
            val bundle = Bundle()
            bundle.putString(Constants.StaffOnlineExam.EXAM_TYPE, examType)
            bundle.putString(Constants.StaffOnlineExam.EXAM_ID, examId)
            bundle.putString(Constants.StaffOnlineExam.TYPE, "add")
            startActivity(Intent(mActivity, ExamwiseQuestionAddActivity::class.java).putExtras(bundle))
        }
    }
}