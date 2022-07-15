package com.edulexa.activity.student.hostel.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.hostel.adapter.HostelDetailAdapter
import com.edulexa.activity.student.hostel.adapter.HostelListAdapter
import com.edulexa.activity.student.hostel.model.HostelResponse
import com.edulexa.activity.student.hostel.model.hostel_detail.HostelDetailResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityHostelDetailStudentBinding
import com.edulexa.databinding.ActivityReportCardDetailBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostelDetailActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHostelDetailStudentBinding? = null
    var hostelName = ""
    var hostelId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostelDetailStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        getHostelStudentList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun getBundleData() {
        try {
            val bundle = intent.extras
            hostelName = bundle!!.getString(Constants.StudentHostel.TITLE)!!
            hostelId = bundle.getString(Constants.StudentHostel.HOSTELID)!!
            binding!!.tvHostelHeaderTitle.text = hostelName
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getHostelStudentList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.HOSTELID, hostelId)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())



            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getHostelDetails")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getHostelDetails(requestBody)
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
                            val statusCode = jsonObjectResponse.optInt("success")
                            if (statusCode == 1){
                                val modelResponse = Utils.getObject(responseStr, HostelDetailResponse::class.java) as HostelDetailResponse
                                if (modelResponse.getData() != null){
                                    if (modelResponse.getData()!!.size > 0){
                                        binding!!.studentHostelDetailRecycler.visibility = View.VISIBLE
                                        binding!!.tvStudentHostelDetailNoData.visibility = View.GONE
                                        binding!!.studentHostelDetailRecycler.layoutManager = LinearLayoutManager(mActivity!!,
                                            RecyclerView.VERTICAL,false)
                                        binding!!.studentHostelDetailRecycler.adapter = HostelDetailAdapter(mActivity!!,modelResponse.getData())
                                    }
                                }else{
                                    binding!!.studentHostelDetailRecycler.visibility = View.GONE
                                    binding!!.tvStudentHostelDetailNoData.visibility = View.VISIBLE
                                }
                            }else {
                                binding!!.studentHostelDetailRecycler.visibility = View.GONE
                                binding!!.tvStudentHostelDetailNoData.visibility = View.VISIBLE
                            }
                        }else{
                            binding!!.studentHostelDetailRecycler.visibility = View.GONE
                            binding!!.tvStudentHostelDetailNoData.visibility = View.VISIBLE
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.studentHostelDetailRecycler.visibility = View.GONE
                        binding!!.tvStudentHostelDetailNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    binding!!.studentHostelDetailRecycler.visibility = View.GONE
                    binding!!.tvStudentHostelDetailNoData.visibility = View.VISIBLE
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