package com.edulexa.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.edulexa.R
import com.edulexa.activity.select_school.activity.SelectSchoolActivity
import com.edulexa.activity.staff.dashboard.activity.DashboardStaffActivity
import com.edulexa.activity.student.dashboard.activity.DashboardStudentActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityLoginBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import java.lang.Exception

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLoginBinding? = null
    var preference : Preference? = null
    var staffStudentType = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
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
        preference!!.putString(Constants.Preference.STUDENT_BASE_URL,Constants.BASE_URL_STUDENT)
        preference!!.putString(Constants.Preference.APIURL_STUDENT,Constants.DOMAIN_STUDENT)
        Constants.BASE_URL_STUDENT = preference!!.getString(Constants.Preference.STUDENT_BASE_URL)!!
        if (Constants.BASE_URL_STUDENT.endsWith("/"))
            preference!!.putString(Constants.Preference.IMAGESURL_STUDENT,Constants.BASE_URL_STUDENT)
        else preference!!.putString(Constants.Preference.IMAGESURL_STUDENT,Constants.BASE_URL_STUDENT + "/")
        Constants.DOMAIN_STUDENT = preference!!.getString(Constants.Preference.APIURL_STUDENT)!!
        Constants.PG_RETURN_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY
        Constants.PG_RETURN_BULK_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY_BALKFEEADD
        Constants.PG_RETURN_TRANSPORT_BULK_URL_STUDENT = Constants.BASE_URL_STUDENT+Constants.API_TRAKNPAY_BALKTRANSPORTFEEADD
        preference!!.putString(Constants.Preference.SCHOOL_LOGO,Constants.BASE_URL_SCHOOL_LOGO)
        Constants.BASE_URL_SCHOOL_LOGO = preference!!.getString(Constants.Preference.SCHOOL_LOGO)!!

        preference!!.putString(Constants.Preference.STAFF_BASE_URL,Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF)
        if (preference!!.getString(Constants.Preference.STAFF_BASE_URL)!!.endsWith("/"))
            Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = preference!!.getString(Constants.Preference.STAFF_BASE_URL)!!
        else Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF = preference!!.getString(Constants.Preference.STAFF_BASE_URL)!! + "/"
        Constants.BASE_URL_STAFF = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + Constants.STAFF_API_WEBSERVICE
        Constants.BASE_URL_WEBVIEW_STAFF = Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + Constants.SITE_WEBVIEWLOGIN_USERNAME

    }

    private fun setUpData(){
        try {
            Utils.setImageUsingGlide(mActivity!!,preference!!.getString(Constants.Preference.SCHOOL_LOGO),binding!!.ivLoginLogo)
            binding!!.tvLoginSchoolName.text = preference!!.getString(Constants.Preference.SCHOOL_NAME)
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