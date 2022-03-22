package com.edulexa.activity.student.profile.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.databinding.ActivityNoticeboardStudentBinding
import com.edulexa.databinding.ActivityProfileStudentBinding

class ProfileStudentActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityProfileStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.languageStudentLay.setOnClickListener(this)
        binding!!.aboutUsStudentLay.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.language_student_lay)
            startActivity(Intent(mActivity,LanguageStudentActivity::class.java))
        else if (id == R.id.about_us_student_lay)
            startActivity(Intent(mActivity,AboutUsStudentActivity::class.java))
    }
}