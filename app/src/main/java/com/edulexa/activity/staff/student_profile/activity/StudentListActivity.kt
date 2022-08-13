package com.edulexa.activity.staff.student_profile.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.student_profile.adapter.ClassListAdapter
import com.edulexa.activity.staff.student_profile.adapter.StudentListAdapter
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.student_list.StudentListResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityStudentListStaffBinding
import com.edulexa.databinding.ActivityStudentProfileClassListStaffBinding
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

class StudentListActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityStudentListStaffBinding? = null
    var classId = ""
    var sectionId = ""
    var preference : Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentListStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        getStudentList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun getBundleData() {
        try {
            val bundle = intent.extras
            classId = bundle!!.getString(Constants.StaffStudentProfile.CLASS_ID)!!
            sectionId = bundle.getString(Constants.StaffStudentProfile.SECTION_ID)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getStudentList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff = APIClientStaff.getRetroFitClientWithNewKeyHeader(mActivity!!,
                Utils.getStaffToken(mActivity!!),
                Utils.getStaffId(mActivity!!),dbId!!).create(ApiInterfaceStaff::class.java)


            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
            builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID,  sectionId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF+"getStudentsByClass")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getStudentsByClass(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){
                            val responseJsonObject = JSONObject(responseStr)
                            val status = responseJsonObject.optInt("status")
                            if (status == 200){
                                val modelResponse = Utils.getObject(responseStr, StudentListResponse::class.java) as StudentListResponse
                                if (modelResponse.getStudentList()!!.isNotEmpty()){
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.GONE
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity, RecyclerView.VERTICAL,false)
                                    binding!!.recyclerView.adapter = StudentListAdapter(mActivity!!,modelResponse.getStudentList())
                                }else{
                                    binding!!.recyclerView.visibility = View.GONE
                                    binding!!.tvNoData.visibility = View.VISIBLE
                                }
                            }else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!,message)
                                else Utils.showToastPopup(mActivity!!,getString(R.string.did_not_fetch_data))
                                binding!!.recyclerView.visibility = View.GONE
                                binding!!.tvNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.recyclerView.visibility = View.GONE
                            binding!!.tvNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.recyclerView.visibility = View.GONE
                        binding!!.tvNoData.visibility = View.VISIBLE
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
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