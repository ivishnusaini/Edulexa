package com.edulexa.activity.student.teacher_rating.activity

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.activity.student.teacher_rating.adapter.TeacherListAdapter
import com.edulexa.activity.student.teacher_rating.model.TeacherListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityApplyForLeaveStudentBinding
import com.edulexa.databinding.ActivityTeacherListStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TeacherListActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityTeacherListStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeacherListStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getTeachersList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getTeachersList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.CLASSID, Utils.getStudentClassId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.SECTIONID,Utils.getStudentSectionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getTeachersList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getTeachersList(requestBody)
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
                            val success = jsonObjectResponse.optInt("success")
                            if (success == 1){
                                val modelResponse = Utils.getObject(responseStr, TeacherListResponse::class.java) as TeacherListResponse
                                if (modelResponse.getData() != null){
                                    if (modelResponse.getData()!!.size > 0){
                                        binding!!.teacherListRecycler.visibility = View.VISIBLE
                                        binding!!.tvTeacherListNoData.visibility = View.GONE
                                        binding!!.teacherListRecycler.layoutManager = LinearLayoutManager(mActivity!!,RecyclerView.VERTICAL,false)
                                        binding!!.teacherListRecycler.adapter = TeacherListAdapter(mActivity!!,modelResponse.getData())
                                    }else{
                                        binding!!.teacherListRecycler.visibility = View.GONE
                                        binding!!.tvTeacherListNoData.visibility = View.VISIBLE
                                    }
                                }else{
                                    binding!!.teacherListRecycler.visibility = View.GONE
                                    binding!!.tvTeacherListNoData.visibility = View.VISIBLE
                                }
                            }else {
                                binding!!.teacherListRecycler.visibility = View.GONE
                                binding!!.tvTeacherListNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.teacherListRecycler.visibility = View.GONE
                            binding!!.tvTeacherListNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.teacherListRecycler.visibility = View.GONE
                        binding!!.tvTeacherListNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.teacherListRecycler.visibility = View.GONE
                    binding!!.tvTeacherListNoData.visibility = View.VISIBLE
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