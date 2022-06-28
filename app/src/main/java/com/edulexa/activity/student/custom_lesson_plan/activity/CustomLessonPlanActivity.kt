package com.edulexa.activity.student.custom_lesson_plan.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.custom_lesson_plan.adapter.CustomLessonPlanAdapter
import com.edulexa.activity.student.custom_lesson_plan.model.CustomLessonPlanResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityCustomLessonPlanBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CustomLessonPlanActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityCustomLessonPlanBinding? = null
    var fromDateSetListener: OnDateSetListener? = null
    var myCalendar: Calendar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomLessonPlanBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpData()
        setUpFromDate()
        getCustomLessonPlanList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvStudentLessonSelectDate.setOnClickListener(this)
    }
    private fun setUpData(){
       binding!!.tvStudentLessonSelectDate.text = Utils.getCurrentDate()
    }
    private fun setUpFromDate() {
        myCalendar = Calendar.getInstance()
        fromDateSetListener =
            OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
                getCustomLessonPlanList()
            }
    }
    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding!!.tvStudentLessonSelectDate.text = sdf.format(myCalendar!!.time)
    }

    private fun getCustomLessonPlanList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.DATE, binding!!.tvStudentLessonSelectDate.text.toString())
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getCustomLession")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getCustomLessonPlan(requestBody)
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
                                val modelResponse = Utils.getObject(responseStr, CustomLessonPlanResponse::class.java) as CustomLessonPlanResponse
                                if (modelResponse.getData() != null && modelResponse.getData()!!.size > 0){
                                    binding!!.studentLessonPlanRecycler.visibility = View.VISIBLE
                                    binding!!.tvStudentLessonNoData.visibility = View.GONE
                                    binding!!.studentLessonPlanRecycler.layoutManager = LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false)
                                    binding!!.studentLessonPlanRecycler.adapter = CustomLessonPlanAdapter(mActivity!!,modelResponse.getData())
                                }else{
                                    binding!!.studentLessonPlanRecycler.visibility = View.GONE
                                    binding!!.tvStudentLessonNoData.visibility = View.VISIBLE
                                }
                            }else {
                                Utils.showToast(mActivity!!,message)
                                binding!!.studentLessonPlanRecycler.visibility = View.GONE
                                binding!!.tvStudentLessonNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.studentLessonPlanRecycler.visibility = View.GONE
                            binding!!.tvStudentLessonNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        Utils.showToast(mActivity!!, e.localizedMessage!!)
                        binding!!.studentLessonPlanRecycler.visibility = View.GONE
                        binding!!.tvStudentLessonNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.studentLessonPlanRecycler.visibility = View.GONE
                    binding!!.tvStudentLessonNoData.visibility = View.VISIBLE
                }
            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_student_lesson_select_date){
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!, fromDateSetListener, myCalendar!!.get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }
    }
}