package com.edulexa.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.databinding.ActivityForgotPasswordBinding
import com.edulexa.databinding.ActivityForgotPasswordEnterOtpBinding

class ForgotPasswordEnterOtpActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityForgotPasswordEnterOtpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordEnterOtpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
    }
    private fun setUpClickListener() {
        binding!!.tvVerify.setOnClickListener(this)
        binding!!.tvCancelForgotPassword.setOnClickListener(this)
    }
    private fun goToCreatePasswordActivity(){
        startActivity(Intent(mActivity!!, ForgotCreatePasswordActivity::class.java))
        finish()
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.tv_cancel_forgot_password)
            onBackPressed()
        else if (id == R.id.tv_verify)
            goToCreatePasswordActivity()
    }
}