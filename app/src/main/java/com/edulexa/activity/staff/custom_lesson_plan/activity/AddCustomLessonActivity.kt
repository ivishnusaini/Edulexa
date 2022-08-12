package com.edulexa.activity.staff.custom_lesson_plan.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list.FormFields
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityAddCustomLessonStaffBinding
import com.edulexa.databinding.ActivityCustomLessonPlanStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class AddCustomLessonActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityAddCustomLessonStaffBinding? = null
    var classId = ""
    var sectionId = ""
    var sectionName = ""
    var subjectId = ""
    var idStr = ""

    var myCalendar: Calendar? = null
    var fromDateSetListener: DatePickerDialog.OnDateSetListener? = null

    var preference : Preference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomLessonStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getBundleData()
        setUpFromDate()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.uploadDateLay.setOnClickListener(this)
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
            }
    }

    private fun updateLabelFromData() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding!!.tvUploadDate.text = sdf.format(myCalendar!!.time)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.upload_date_lay) {
            binding!!.tvUploadDate.text = getString(R.string.custom_lesson_plan_staff_date)
            Utils.hideKeyboard(mActivity!!)
            val fromDateDialog = DatePickerDialog(
                mActivity!!,
                fromDateSetListener,
                myCalendar!!.get(Calendar.YEAR),
                myCalendar!!.get(Calendar.MONTH),
                myCalendar!!.get(Calendar.DAY_OF_MONTH)
            )
            fromDateDialog.show()
        }
    }
}