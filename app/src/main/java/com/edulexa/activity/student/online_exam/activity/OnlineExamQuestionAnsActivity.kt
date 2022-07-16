package com.edulexa.activity.student.online_exam.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.online_exam.model.exam_list.OnlineExamListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityOnlineExamDetailStudentBinding
import com.edulexa.databinding.ActivityOnlineExamQuestionAnsStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnlineExamQuestionAnsActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityOnlineExamQuestionAnsStudentBinding? = null
    var examId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineExamQuestionAnsStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        getQuestionAnsList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getBundleData(){
        try{
            val bundle = intent.extras
            examId = bundle!!.getString(Constants.StudentOnlineExam.EXAMID)!!
            val examName = bundle.getString(Constants.StudentOnlineExam.EXAM_NAME)!!
            binding!!.tvOnlineExamQuestionAnsTitle.text = examName
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun getQuestionAnsList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.EXAM_ID, examId)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/startOnlineExam")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getOnlineExamQuestionAnsList(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){

                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }
            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}