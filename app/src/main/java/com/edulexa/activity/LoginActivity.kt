package com.edulexa.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.edulexa.R
import com.edulexa.activity.select_school.activity.SelectSchoolActivity
import com.edulexa.activity.staff.dashboard.activity.DashboardStaffActivity
import com.edulexa.activity.student.dashboard.activity.DashboardStudentActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityLoginBinding
import com.edulexa.support.Utils
import java.lang.Exception

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLoginBinding? = null
    var staffStudentType = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        setBaseUrl()
        setUpData()
    }

    private fun setUpClickListener() {
        binding!!.tvBack.setOnClickListener(this)
        binding!!.tvStaff.setOnClickListener(this)
        binding!!.tvStudent.setOnClickListener(this)
        binding!!.tvForgotPassword.setOnClickListener(this)
        binding!!.tvSubmit.setOnClickListener(this)
    }

    private fun setBaseUrl(){
        if (Utils.getStudentBaseUrl(mActivity!!).endsWith("/"))
            Constants.BASE_URL_STUDENT = Utils.getStudentBaseUrl(mActivity!!)
        else Constants.BASE_URL_STUDENT = Utils.getStudentBaseUrl(mActivity!!) + "/"
        if (Constants.BASE_URL_STUDENT.endsWith(("/")))
            Constants.IMAGESURL_STUDENT = Constants.BASE_URL_STUDENT
        else Constants.IMAGESURL_STUDENT = Constants.BASE_URL_STUDENT + "/"
        Constants.BASE_URL_SCHOOL_LOGO_STUDENT = Utils.getSchoolLogo(mActivity!!)

        if (Utils.getStaffBaseUrl(mActivity!!).endsWith("/"))
            Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = Utils.getStaffBaseUrl(mActivity!!)
        else Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = Utils.getStaffBaseUrl(mActivity!!) + "/"
    }

    private fun setUpData(){
        try {
            Utils.setImageUsingGlide(mActivity!!,Utils.getSchoolLogo(mActivity!!),binding!!.ivLoginLogo)
            binding!!.tvLoginSchoolName.text = Utils.getStudentSchoolName(mActivity!!)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun resetStaffStudentButton(type: String) {
        binding!!.tvStaff.setBackgroundResource(R.drawable.edit_text_bg_25)
        binding!!.tvStudent.setBackgroundResource(R.drawable.edit_text_bg_25)
        binding!!.tvStaff.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.tvStudent.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        when (type) {
            "staff" -> {
                staffStudentType = "staff"
                binding!!.tvStaff.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvStaff.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
            "student" -> {
                staffStudentType = "student"
                binding!!.tvStudent.setBackgroundResource(R.drawable.bg_button_25)
                binding!!.tvStudent.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
            }
        }
    }

    private fun goToHomeActivity() {
        if (staffStudentType.equals("staff")) {
            startActivity(Intent(mActivity!!, DashboardStaffActivity::class.java))
            finish()
        } else {
            startActivity(Intent(mActivity!!, DashboardStudentActivity::class.java))
            finish()
        }
    }

    private fun goToForgotPasswordActivity(){
        startActivity(Intent(mActivity!!, ForgotPasswordActivity::class.java))
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.tv_back)
            startActivity(Intent(mActivity!!, SelectSchoolActivity::class.java))
        else if (id == R.id.tv_staff)
            resetStaffStudentButton("staff")
        else if (id == R.id.tv_student)
            resetStaffStudentButton("student")
        else if (id == R.id.tv_submit)
            goToHomeActivity()
        else if (id == R.id.tv_forgot_password)
            goToForgotPasswordActivity()
    }
}