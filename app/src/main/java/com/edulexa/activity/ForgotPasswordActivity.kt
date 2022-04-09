package com.edulexa.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.activity.student.dashboard.activity.DashboardStudentActivity
import com.edulexa.databinding.ActivityForgotPasswordBinding
import com.edulexa.databinding.ActivityLoginBinding

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityForgotPasswordBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding!!.tvGenerateOtp.setOnClickListener(this)
        binding!!.tvCancelForgotPassword.setOnClickListener(this)
    }

    private fun goToEnterOtpActivity() {
        startActivity(Intent(mActivity!!, ForgotPasswordEnterOtpActivity::class.java))
        finish()
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.tv_cancel_forgot_password)
            onBackPressed()
        else if (id == R.id.tv_generate_otp)
            goToEnterOtpActivity()
    }
}