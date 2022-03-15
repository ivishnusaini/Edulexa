package com.edulexa.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.databinding.ActivitySelectSchoolBinding
import com.edulexa.support.Utils


class SelectSchoolActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySelectSchoolBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectSchoolBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        startAnimation()
    }

    private fun setUpClickListener() {
        binding!!.ivExit.setOnClickListener(this)
        binding!!.btnSubmit.setOnClickListener(this)
    }

    private fun startAnimation() {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.enter_school_code_side_slide)
        binding!!.cvEnterSchoolCode.startAnimation(slideAnimation)
    }

    private fun submit() {
        Utils.hideKeyboard(mActivity!!)
        val schoolCodeStr = binding!!.etSchoolCode.text.toString().trim()
        if (schoolCodeStr.isEmpty())
            Utils.showSnackBar(mActivity!!, getString(R.string.enter_school_code_empty_validation))
        else {
            startActivity(Intent(mActivity!!,LoginActivity::class.java))
            finish()
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_exit)
            finish()
        else if (id == R.id.btn_submit)
            submit()
    }
}