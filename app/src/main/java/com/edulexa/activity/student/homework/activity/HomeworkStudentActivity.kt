package com.edulexa.activity.student.homework.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.databinding.ActivityHomeworkStudentBinding

class HomeworkStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHomeworkStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeworkStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpTabLayoutData()
    }
    private fun setUpClickListener(){
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun setUpTabLayoutData(){
        for (i in 0 until 10) {
            binding!!.tabLayoutHomework.setTag("Test")
        }
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            finish()
    }
}