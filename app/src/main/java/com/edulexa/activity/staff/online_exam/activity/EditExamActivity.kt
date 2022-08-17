package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.api.APIClientStaff
import com.edulexa.api.ApiInterfaceStaff
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityEditExamStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditExamActivity : AppCompatActivity(),View.OnClickListener {
    var binding: ActivityEditExamStaffBinding? = null
    var mActivity: Activity? = null
    var preference: Preference? = null

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null
    var toDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var examId = ""
    var onlineExamId = ""
    var examModelStr = ""
    var examType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditExamStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        setUpFromDate()
        setUpToDate()
        setUpData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvFromDate.setOnClickListener(this)
        binding!!.tvToDate.setOnClickListener(this)
        binding!!.tvFromTime.setOnClickListener(this)
        binding!!.tvToTime.setOnClickListener(this)
        binding!!.btnSave.setOnClickListener(this)
    }
    private fun getBundleData() {
        try {
            val bundle = intent.extras
            onlineExamId = bundle!!.getString(Constants.StaffOnlineExam.ONLINE_EXAM_ID)!!
            examModelStr = bundle.getString(Constants.StaffOnlineExam.EXAM_MODEL)!!
            val examTypeStr = bundle.getString(Constants.StaffOnlineExam.EXAM_TYPE)!!
            examType = if (examTypeStr == "0")
                "Online"
            else "Practice"
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
                updateLabelFromData(binding!!.tvFromDate)
            }
    }

    private fun setUpToDate() {
        myCalendar = Calendar.getInstance()
        toDateSetListener =
            DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabelFromData(binding!!.tvToDate)
            }
    }

    private fun updateLabelFromData(textView : TextView) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textView.text = sdf.format(myCalendar!!.time)
    }

    private fun setUpData(){
        try{
            val examModel = Utils.getObject(examModelStr, ExamOnlineExamStaff::class.java) as ExamOnlineExamStaff
            if (examType == "Practice"){
                binding!!.fromToDateLay.visibility = View.GONE
                binding!!.fromToTimeLay.visibility = View.GONE
                binding!!.passingMarksLay.visibility = View.GONE
                examId = examModel.getId()!!
            }else{
                binding!!.fromToDateLay.visibility = View.VISIBLE
                binding!!.fromToTimeLay.visibility = View.VISIBLE
                binding!!.passingMarksLay.visibility = View.VISIBLE
                val startDateAndTime: List<String> = examModel.getExamFrom()!!.split(" ")
                val endDateAndTime: List<String> = examModel.getExamTo()!!.split(" ")
                binding!!.tvFromDate.text = startDateAndTime[0]
                binding!!.tvToDate.text = endDateAndTime[0]
                binding!!.tvFromTime.text = startDateAndTime[1]
                binding!!.tvToTime.text = endDateAndTime[1]
                binding!!.etExamPassingMarks.setText(examModel.getPassingPercentage())
            }
            binding!!.etExamTitle.setText(examModel.getExam())
            binding!!.etExamDuration.setText(examModel.getDuration())
            binding!!.etExamDescription.setText(examModel.getDescription())
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun saveOnlineExam(){
        val titleStr = binding!!.etExamTitle.text.toString().trim()
        val fromDateStr = binding!!.tvFromDate.text.toString().trim()
        val toDateStr = binding!!.tvToDate.text.toString().trim()
        val fromTimeStr = binding!!.tvFromTime.text.toString().trim()
        val toTimeStr = binding!!.tvToTime.text.toString().trim()
        val durationStr = binding!!.etExamTitle.text.toString().trim()
        val passingMarksStr = binding!!.etExamPassingMarks.text.toString().trim()
        val descriptionStr = binding!!.etExamDescription.text.toString().trim()
        val radioButton: RadioButton = binding!!.radioGroup.findViewById(binding!!.radioGroup.getCheckedRadioButtonId())
        val marksType = radioButton.text.toString()
        if (titleStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_title_validation))
        else if (fromDateStr == getString(R.string.online_exam_staff_from_date))
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_from_date_validation))
        else if (toDateStr == getString(R.string.online_exam_staff_to_date))
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_to_date_validation))
        else if (fromTimeStr == getString(R.string.online_exam_staff_from_time))
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_from_time_validation))
        else if (toTimeStr == getString(R.string.online_exam_staff_to_time))
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_to_time_validation))
        else if (durationStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_duration_validation))
        else if (descriptionStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_description_validation))
        else if (passingMarksStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_passing_details_validation))
        else{
            val examFrom = "$fromDateStr $fromTimeStr"
            val examTo = "$toDateStr $toTimeStr"
            if (Utils.isNetworkAvailable(mActivity!!)) {
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)

                val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

                val apiInterfaceWithHeader: ApiInterfaceStaff =
                    APIClientStaff.getRetroFitClientWithNewKeyHeader(
                        mActivity!!,
                        Utils.getStaffToken(mActivity!!),
                        Utils.getStaffId(mActivity!!),
                        dbId!!
                    ).create(ApiInterfaceStaff::class.java)


                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(Constants.ParamsStaff.EXAM, titleStr)
                builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
                builder.addFormDataPart(Constants.ParamsStaff.EXAM_ID, onlineExamId)
                builder.addFormDataPart(Constants.ParamsStaff.IS_ACTIVE, "1")
                builder.addFormDataPart(Constants.ParamsStaff.PUBLISH_RESULT, "0")
                builder.addFormDataPart(Constants.ParamsStaff.EXAM_FROM, examFrom)
                builder.addFormDataPart(Constants.ParamsStaff.EXAM_TO, examTo)
                builder.addFormDataPart(Constants.ParamsStaff.DURATION, durationStr)
                builder.addFormDataPart(Constants.ParamsStaff.DESCRIPTION, descriptionStr)
                builder.addFormDataPart(Constants.ParamsStaff.PASSING_PERCENTAGE, passingMarksStr)
                builder.addFormDataPart(Constants.ParamsStaff.PASSING_MARK_TYPE, marksType)
                val requestBody = builder.build()


                Utils.printLog("Url", Constants.BASE_URL_STAFF + "editOnlineExam")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.editOnlineExam(requestBody)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Utils.hideProgressBar()
                        try {
                            val responseStr = response.body()!!.string()
                            if (!responseStr.isNullOrEmpty()) {
                                val responseJsonObject = JSONObject(responseStr)
                                val message = responseJsonObject.optString("message")
                                Utils.showToast(mActivity!!, message)
                                onBackPressed()
                            } else {
                                Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.response_null_or_empty_validation)
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Utils.hideProgressBar()
                        Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    }

                })
            } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

        }
    }

    private fun savePracticeExam(){
        val titleStr = binding!!.etExamTitle.text.toString().trim()
        val timeInMinuteStr = binding!!.etExamDuration.text.toString().trim()
        val descriptionStr = binding!!.etExamDescription.text.toString().trim()
        if (titleStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_title_validation))
        else if (timeInMinuteStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_duration_validation))
        else if (timeInMinuteStr.isEmpty())
            Utils.showToastPopup(mActivity!!,getString(R.string.online_exam_staff_description_validation))
        else{
            if (Utils.isNetworkAvailable(mActivity!!)) {
                Utils.showProgressBar(mActivity!!)
                Utils.hideKeyboard(mActivity!!)

                val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

                val apiInterfaceWithHeader: ApiInterfaceStaff =
                    APIClientStaff.getRetroFitClientWithNewKeyHeader(
                        mActivity!!,
                        Utils.getStaffToken(mActivity!!),
                        Utils.getStaffId(mActivity!!),
                        dbId!!
                    ).create(ApiInterfaceStaff::class.java)


                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(Constants.ParamsStaff.EXAM, titleStr)
                builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
                builder.addFormDataPart(Constants.ParamsStaff.IS_ACTIVE, "1")
                builder.addFormDataPart(Constants.ParamsStaff.PUBLISH_RESULT, "0")
                builder.addFormDataPart(Constants.ParamsStaff.DURATION, timeInMinuteStr)
                builder.addFormDataPart(Constants.ParamsStaff.DESCRIPTION, descriptionStr)
                builder.addFormDataPart(Constants.ParamsStaff.ID, examId)
                val requestBody = builder.build()


                Utils.printLog("Url", Constants.BASE_URL_STAFF + "editPracticeExam")

                val call: Call<ResponseBody> = apiInterfaceWithHeader.editPracticeExam(requestBody)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Utils.hideProgressBar()
                        try {
                            val responseStr = response.body()!!.string()
                            if (!responseStr.isNullOrEmpty()) {
                                val responseJsonObject = JSONObject(responseStr)
                                val message = responseJsonObject.optString("message")
                                Utils.showToast(mActivity!!, message)
                                onBackPressed()
                            } else {
                                Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.response_null_or_empty_validation)
                                )
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Utils.hideProgressBar()
                        Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    }

                })
            } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_from_date) {
            binding!!.tvFromDate.text = getString(R.string.online_exam_staff_from_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                fromDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }else if (id == R.id.tv_to_date) {
            binding!!.tvToDate.text = getString(R.string.online_exam_staff_to_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                toDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }else if (id == R.id.tv_from_time){
            val hour = myCalendar!![Calendar.HOUR_OF_DAY]
            val minute = myCalendar!![Calendar.MINUTE]
            val mTimePicker = TimePickerDialog(mActivity,
                { timePicker, selectedHour, selectedMinute -> binding!!.tvFromTime.text = "$selectedHour:$selectedMinute:00" },
                hour,
                minute,
                false
            )
            mTimePicker.show()
        }else if (id == R.id.tv_to_time){
            val hour = myCalendar!![Calendar.HOUR_OF_DAY]
            val minute = myCalendar!![Calendar.MINUTE]
            val mTimePicker = TimePickerDialog(mActivity,
                { timePicker, selectedHour, selectedMinute -> binding!!.tvToTime.text = "$selectedHour:$selectedMinute:00" },
                hour,
                minute,
                false
            )
            mTimePicker.show()
        }else if (id == R.id.btn_save){
            if (examType == "Online")
                saveOnlineExam()
            else savePracticeExam()
        }
    }
}