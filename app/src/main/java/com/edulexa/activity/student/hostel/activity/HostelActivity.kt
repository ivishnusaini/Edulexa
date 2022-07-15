package com.edulexa.activity.student.hostel.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.activity.student.hostel.adapter.HostelListAdapter
import com.edulexa.activity.student.hostel.model.HostelResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityHomeworkStudentBinding
import com.edulexa.databinding.ActivityHostelStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostelActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHostelStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostelStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getHostelList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun getHostelList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getHostelList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getHostelList()
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
                                val modelResponse = Utils.getObject(responseStr, HostelResponse::class.java) as HostelResponse
                                if (modelResponse.getData() != null){
                                    if (modelResponse.getData()!!.size > 0){
                                        binding!!.studentHostelRecycler.visibility = View.VISIBLE
                                        binding!!.tvStudentHostelNoData.visibility = View.GONE
                                        binding!!.studentHostelRecycler.layoutManager = LinearLayoutManager(mActivity!!,RecyclerView.VERTICAL,false)
                                        binding!!.studentHostelRecycler.adapter = HostelListAdapter(mActivity!!,modelResponse.getData())
                                    }
                                }else{
                                    binding!!.studentHostelRecycler.visibility = View.GONE
                                    binding!!.tvStudentHostelNoData.visibility = View.VISIBLE
                                }
                            }else {
                                binding!!.studentHostelRecycler.visibility = View.GONE
                                binding!!.tvStudentHostelNoData.visibility = View.VISIBLE
                            }
                        }else{
                            binding!!.studentHostelRecycler.visibility = View.GONE
                            binding!!.tvStudentHostelNoData.visibility = View.VISIBLE
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.studentHostelRecycler.visibility = View.GONE
                        binding!!.tvStudentHostelNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    binding!!.studentHostelRecycler.visibility = View.GONE
                    binding!!.tvStudentHostelNoData.visibility = View.VISIBLE
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