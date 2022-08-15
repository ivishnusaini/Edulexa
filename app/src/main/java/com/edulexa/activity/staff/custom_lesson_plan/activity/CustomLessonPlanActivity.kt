package com.edulexa.activity.staff.custom_lesson_plan.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.Window
import android.widget.DatePicker
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.adapter.CustomLessonPlanStaffAdapter
import com.edulexa.activity.staff.custom_lesson_plan.adapter.SelectSubjectCustomLessonAdapter
import com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list.CustomLessonPlanResponse
import com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list.DatumCustomLesson
import com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list.FormFields
import com.edulexa.activity.staff.custom_lesson_plan.model.subject.SubjectResponse
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityCustomLessonClassListStaffBinding
import com.edulexa.databinding.ActivityCustomLessonPlanStaffBinding
import com.edulexa.databinding.DialogStudentCustomLessonInfoBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomLessonPlanActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityCustomLessonPlanStaffBinding? = null
    var classId = ""
    var sectionId = ""
    var sectionName = ""
    var subjectId = ""
    var idStr = ""

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var preference : Preference? = null
    val lessonPlanList : List<DatumCustomLesson?> = ArrayList()

    var modelResponse : CustomLessonPlanResponse?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomLessonPlanStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        getLessonList()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        setUpFromDate()
        setUpData()
        Utils.showProgressBar(mActivity!!)
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvDate.setOnClickListener(this)
        binding!!.ivAdd.setOnClickListener(this)
    }

    private fun getBundleData(){
        try {
            val bundle = intent.extras
            classId = bundle!!.getString(Constants.StaffCustomLessonPlan.CLASS_ID)!!
            sectionId = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_ID)!!
            sectionName = bundle.getString(Constants.StaffCustomLessonPlan.SECTION_NAME)!!
            subjectId = bundle.getString(Constants.StaffCustomLessonPlan.SUBJECT_ID)!!
            idStr = bundle.getString(Constants.StaffCustomLessonPlan.ID)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpFromDate() {
        myCalendar = Calendar.getInstance()
        fromDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData()
                getLessonList()
            }
    }

    private fun updateLabelFromData() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding!!.tvDate.text = sdf.format(myCalendar!!.time)
    }

    private fun setUpData(){
        binding!!.tvDate.text = Utils.getCurrentDate()
    }


    private fun getLessonList(){
        (lessonPlanList as ArrayList<DatumCustomLesson?>).clear()
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff = APIClientStaff.getRetroFitClientWithNewKeyHeader(mActivity!!,
                Utils.getStaffToken(mActivity!!),
                Utils.getStaffId(mActivity!!),dbId!!).create(ApiInterfaceStaff::class.java)

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.ROLE_ID, Utils.getStaffRoleId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID, classId)
            builder.addFormDataPart(Constants.ParamsStaff.SECTION_ID, sectionId)
            builder.addFormDataPart(Constants.ParamsStaff.SUBJECT_ID, subjectId)
            builder.addFormDataPart(Constants.ParamsStaff.DATE,binding!!.tvDate.text.toString())
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF+"getcustomlessonplan")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getcustomlessonplan(requestBody)
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
                                modelResponse = Utils.getObject(responseStr, CustomLessonPlanResponse::class.java) as CustomLessonPlanResponse
                                if (modelResponse!!.getData()!!.isNotEmpty()){
                                    binding!!.recyclerView.visibility = View.VISIBLE
                                    binding!!.tvNoData.visibility = View.GONE
                                    lessonPlanList.addAll(modelResponse!!.getData()!!)
                                    binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity, RecyclerView.VERTICAL,false)
                                    binding!!.recyclerView.adapter = CustomLessonPlanStaffAdapter(mActivity!!,lessonPlanList)
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

    fun showInfoDialog(comprensiveStr : String,generalObjectiveStr : String,previousKnowledgeStr : String,teachingMethodStr : String){
            try {
                val dialog = Dialog(mActivity!!)
                var dialogBinding : DialogStudentCustomLessonInfoBinding? = null
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogBinding = DialogStudentCustomLessonInfoBinding.inflate(layoutInflater)
                dialog.setContentView(dialogBinding.root)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCanceledOnTouchOutside(false)
                dialogBinding.presentationLay.visibility = View.GONE
                dialogBinding.tvComprehensiveQuestion.text = Html.fromHtml(comprensiveStr)
                dialogBinding.tvGeneralObjective.text = Html.fromHtml(generalObjectiveStr)
                dialogBinding.tvPreviousKnowledge.text = Html.fromHtml(previousKnowledgeStr)
                dialogBinding.tvTeachingMethod.text = Html.fromHtml(teachingMethodStr)
                dialogBinding.cvOk.setOnClickListener { v: View? -> dialog.dismiss() }
                if (dialog.isShowing) dialog.dismiss()
                dialog.show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
    }

    fun deleteLesson(lessonId : String,position : Int){
        try {
            val dialog = Dialog(mActivity!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_exit)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
            tvMessage.text = getString(R.string.add_custom_lesson_plan_staff_delete_consent)
            val cvCancel: CardView = dialog.findViewById(R.id.cv_cancel)
            val cvOk: CardView = dialog.findViewById(R.id.cv_ok)
            cvCancel.setOnClickListener { v: View? -> dialog.dismiss() }
            cvOk.setOnClickListener { v: View? ->
                if (Utils.isNetworkAvailable(mActivity!!)){
                    Utils.showProgressBar(mActivity!!)
                    Utils.hideKeyboard(mActivity!!)

                    val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

                    val apiInterfaceWithHeader: ApiInterfaceStaff = APIClientStaff.getRetroFitClientWithNewKeyHeader(mActivity!!,
                        Utils.getStaffToken(mActivity!!),
                        Utils.getStaffId(mActivity!!),dbId!!).create(ApiInterfaceStaff::class.java)

                    val builder = MultipartBody.Builder()
                    builder.setType(MultipartBody.FORM)
                    builder.addFormDataPart(Constants.ParamsStaff.LESSON_ID, lessonId)
                    val requestBody = builder.build()

                    Utils.printLog("Url", Constants.BASE_URL_STAFF+"deleteCustomLessonPlan")

                    val call: Call<ResponseBody> = apiInterfaceWithHeader.deleteCustomLessonPlan(requestBody)
                    call.enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            Utils.hideProgressBar()
                            try{
                                val responseStr = response.body()!!.string()
                                if (!responseStr.isNullOrEmpty()){
                                    (lessonPlanList as ArrayList<DatumCustomLesson?>).removeAt(position)
                                    if (lessonPlanList.isNotEmpty()){
                                        binding!!.recyclerView.visibility = View.VISIBLE
                                        binding!!.tvNoData.visibility = View.GONE
                                        binding!!.recyclerView.adapter = CustomLessonPlanStaffAdapter(mActivity!!,lessonPlanList)
                                    }else{
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

                dialog.dismiss()
            }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_date){
            binding!!.tvDate.text = getString(R.string.custom_lesson_plan_staff_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!, fromDateSetListener, myCalendar!!.get(Calendar.YEAR), myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }else if (id == R.id.iv_add){
            var formField : FormFields? = null
            if (modelResponse != null){
                if (modelResponse!!.getData() != null){
                    if (modelResponse!!.getFormFields() != null)
                        formField = modelResponse!!.getFormFields()
                }
            }
            var formFieldStr = ""
            if (formField != null)
                formFieldStr = Gson().toJson(formField)

            val bundle = Bundle()
            bundle.putString(Constants.StaffCustomLessonPlan.FORM_FIELD, formFieldStr)
            bundle.putString(Constants.StaffCustomLessonPlan.CLASS_ID, classId)
            bundle.putString(Constants.StaffCustomLessonPlan.SECTION_ID, sectionId)
            bundle.putString(Constants.StaffCustomLessonPlan.SECTION_NAME, sectionName)
            bundle.putString(Constants.StaffCustomLessonPlan.ID, idStr)
            bundle.putString(Constants.StaffCustomLessonPlan.SUBJECT_ID, subjectId)
            startActivity(Intent(mActivity, AddCustomLessonActivity::class.java).putExtras(bundle))
        }
    }
}