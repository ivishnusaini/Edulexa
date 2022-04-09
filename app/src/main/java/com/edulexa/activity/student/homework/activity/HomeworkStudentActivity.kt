package com.edulexa.activity.student.homework.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentNoticeBoardAdapter
import com.edulexa.activity.student.homework.adapter.HomeworkStudentAdapter
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
        setUpHomeworkData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun setUpHomeworkData(){
        binding!!.studentHomeworkRecycler.layoutManager =
            LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
        binding!!.studentHomeworkRecycler.adapter =
            HomeworkStudentAdapter(mActivity!!)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}