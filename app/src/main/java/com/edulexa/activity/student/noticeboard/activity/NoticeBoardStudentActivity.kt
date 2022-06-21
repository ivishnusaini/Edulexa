package com.edulexa.activity.student.noticeboard.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.edulexa.R
import com.edulexa.activity.student.noticeboard.adapter.NoticeBoardAdapter
import com.edulexa.activity.student.noticeboard.model.NoticeboardResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityNoticeboardStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NoticeBoardStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityNoticeboardStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeboardStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpExamList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun setUpExamList() {
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
            jsonObject.put(Constants.ParamsStudent.SECTIONID,Utils.getStudentSectionId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.CLASSID, Utils.getStudentClassId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getNotifications")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getNoticeboardData(requestBody)
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
                            val statusCode = jsonObjectResponse.optInt("status")
                            val message = jsonObjectResponse.optString("message")
                            if (statusCode == 200){
                                val modelResponse = Utils.getObject(responseStr, NoticeboardResponse::class.java) as NoticeboardResponse
                                if (modelResponse.getData() != null){
                                    if (modelResponse.getData()!!.size > 0){
                                        binding!!.recyclerViewNoticeBoard.visibility = View.VISIBLE
                                        binding!!.tvNoticeBoardNoData.visibility = View.GONE
                                        binding!!.recyclerViewNoticeBoard.layoutManager = StaggeredGridLayoutManager(
                                            2,
                                            LinearLayoutManager.VERTICAL
                                        )
                                        binding!!.recyclerViewNoticeBoard.adapter = NoticeBoardAdapter(mActivity!!,modelResponse.getData())
                                    }
                                }else{
                                    binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                                    binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                                }
                            }else {
                                Utils.showToast(mActivity!!,message)
                                binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                                binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                            binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                        binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerViewNoticeBoard.visibility = View.GONE
                    binding!!.tvNoticeBoardNoData.visibility = View.VISIBLE
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