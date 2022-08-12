package com.edulexa.activity.staff.custom_lesson_plan.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.SelectSubjectCustomLessonAdapter
import com.edulexa.activity.staff.custom_lesson_plan.model.subject.SubjectResponse
import com.edulexa.activity.staff.student_profile.adapter.StudentListAdapter
import com.edulexa.activity.staff.student_profile.model.student_list.StudentListResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivitySelectSubjectStaffBinding
import com.edulexa.databinding.ActivityStudentListStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectSubjectActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySelectSubjectStaffBinding? = null
    var classId = ""
    var sectionId = ""
    var sectionName = ""
    var id = ""
    var preference : Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSubjectStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        getSubjectList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun getBundleData() {
        try {
            val bundle = intent.extras
            classId = bundle!!.getString(Constants.StaffCustomLessonPlan.CLASS_ID)!!
            sectionId = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_ID)!!
            sectionName = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_NAME)!!
            id = bundle.getString(Constants.StaffCustomLessonPlan.ID)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getSubjectList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff = APIClientStaff.getRetroFitClientWithNewKeyHeader(mActivity!!,
                Utils.getStaffToken(mActivity!!),
                Utils.getStaffId(mActivity!!),dbId!!).create(ApiInterfaceStaff::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStaff.CLASS_ID, classId)
            jsonObject.put(Constants.ParamsStaff.SECTION_ID, sectionId)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

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
                                val modelResponse = Utils.getObject(responseStr, SubjectResponse::class.java) as SubjectResponse
                                if (modelResponse.getSubjectList()!!.isNotEmpty()){
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoSubject.visibility = View.GONE
                                    binding!!.recyclerView.layoutManager = GridLayoutManager(mActivity,3, RecyclerView.VERTICAL,false)
                                    binding!!.recyclerView.adapter = SelectSubjectCustomLessonAdapter(mActivity!!,modelResponse.getSubjectList())
                                }else{
                                    binding!!.recyclerView.visibility = View.GONE
                                    binding!!.tvNoSubject.visibility = View.VISIBLE
                                }
                            }else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!,message)
                                else Utils.showToastPopup(mActivity!!,getString(R.string.did_not_fetch_data))
                                binding!!.recyclerView.visibility = View.GONE
                                binding!!.tvNoSubject.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.recyclerView.visibility = View.GONE
                            binding!!.tvNoSubject.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.recyclerView.visibility = View.GONE
                        binding!!.tvNoSubject.visibility = View.VISIBLE
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoSubject.visibility = View.VISIBLE
                }

            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    fun selectSubject(subjectId : String){
        val bundle = Bundle()
        bundle.putString(Constants.StaffCustomLessonPlan.CLASS_ID, classId)
        bundle.putString(Constants.StaffCustomLessonPlan.SECTION_ID, sectionId)
        bundle.putString(Constants.StaffCustomLessonPlan.SECTION_NAME, sectionName)
        bundle.putString(Constants.StaffCustomLessonPlan.ID, id)
        bundle.putString(Constants.StaffCustomLessonPlan.SUBJECT_ID, subjectId)
        startActivity(Intent(mActivity, CustomLessonPlanActivity::class.java).putExtras(bundle))
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}