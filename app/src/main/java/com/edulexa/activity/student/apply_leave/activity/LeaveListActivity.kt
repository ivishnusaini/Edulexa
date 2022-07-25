package com.edulexa.activity.student.apply_leave.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.apply_leave.adapter.LeaveListAdapter
import com.edulexa.activity.student.apply_leave.model.LeaveListResponse
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.online_exam.model.exam_list.OnlineExamListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityLeaveListStudentBinding
import com.edulexa.databinding.ActivityOnlineExamStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeaveListActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLeaveListStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaveListStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getLeaveList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getLeaveList(){
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

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getLeaveList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getLeaveList(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){
                            val jsonObjectResponse = JSONObject(responseStr)
                            val status = jsonObjectResponse.optInt("status")
                            if (status == 1){
                                val modelResponse = Utils.getObject(responseStr, LeaveListResponse::class.java) as LeaveListResponse
                                if (modelResponse.getData() != null){
                                    if (modelResponse.getData()!!.getList() != null
                                        && modelResponse.getData()!!.getList()!!.size > 0){
                                        binding!!.applyLeaveRecycler.visibility = View.VISIBLE
                                        binding!!.tvApplyLeaveNoData.visibility = View.GONE
                                        binding!!.applyLeaveRecycler.layoutManager = LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false)
                                        binding!!.applyLeaveRecycler.adapter = LeaveListAdapter(mActivity!!,modelResponse.getData()!!.getList())
                                    }else{
                                        binding!!.applyLeaveRecycler.visibility = View.GONE
                                        binding!!.tvApplyLeaveNoData.visibility = View.VISIBLE
                                    }
                                }else{
                                    binding!!.applyLeaveRecycler.visibility = View.GONE
                                    binding!!.tvApplyLeaveNoData.visibility = View.VISIBLE
                                }
                            }else{
                                binding!!.applyLeaveRecycler.visibility = View.GONE
                                binding!!.tvApplyLeaveNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.applyLeaveRecycler.visibility = View.GONE
                            binding!!.tvApplyLeaveNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.applyLeaveRecycler.visibility = View.GONE
                        binding!!.tvApplyLeaveNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.applyLeaveRecycler.visibility = View.GONE
                    binding!!.tvApplyLeaveNoData.visibility = View.VISIBLE
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