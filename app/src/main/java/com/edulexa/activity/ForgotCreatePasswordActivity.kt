package com.edulexa.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.databinding.ActivityForgotCreatePasswordBinding
import com.edulexa.databinding.ActivityForgotPasswordEnterOtpBinding

class ForgotCreatePasswordActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityForgotCreatePasswordBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotCreatePasswordBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding!!.tvSubmitForgotPassword.setOnClickListener(this)
        binding!!.tvCancelForgotPassword.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.tv_cancel_forgot_password)
            onBackPressed()
        else if (id == R.id.tv_submit_forgot_password)
            onBackPressed()
    }
}