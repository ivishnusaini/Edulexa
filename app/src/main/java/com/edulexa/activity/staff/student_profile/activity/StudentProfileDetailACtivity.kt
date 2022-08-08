package com.edulexa.activity.staff.student_profile.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityStudentListStaffBinding
import com.edulexa.databinding.ActivityStudentProfileDetailStaffBinding

class StudentProfileDetailACtivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityStudentProfileDetailStaffBinding? = null
    var studentId = ""
    var studentImage = ""
    var studentName = ""
    var studentRollNo = ""
    var studentClass = ""
    var studentSection = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileDetailStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpStudentProfileData()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun getBundleData() {
        try {
            val bundle = intent.extras
            studentId = bundle!!.getString(Constants.StaffStudentProfile.STUDENT_ID)!!
            studentImage = bundle.getString(Constants.StaffStudentProfile.IMAGE)!!
            studentName = bundle.getString(Constants.StaffStudentProfile.NAME)!!
            studentRollNo = bundle.getString(Constants.StaffStudentProfile.ROLL_NO)!!
            studentClass = bundle.getString(Constants.StaffStudentProfile.CLASS)!!
            studentSection = bundle.getString(Constants.StaffStudentProfile.SECTION)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpStudentProfileData(){

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}