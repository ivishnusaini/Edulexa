package com.edulexa.activity.student.custom_lesson_plan.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.custom_lesson_plan.adapter.CustomLessonPlanAdapter
import com.edulexa.databinding.ActivityAttendanceStudentBinding
import com.edulexa.databinding.ActivityCustomLessonPlanBinding

class CustomLessonPlanActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityCustomLessonPlanBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomLessonPlanBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpData()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun setUpData(){
        binding!!.studentLessonPlanRecycler.layoutManager = LinearLayoutManager(mActivity,RecyclerView.VERTICAL,false)
        binding!!.studentLessonPlanRecycler.adapter = CustomLessonPlanAdapter(mActivity!!)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}