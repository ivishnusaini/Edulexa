package com.edulexa.activity.student.examination.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.examination.adapter.ExamAdapter
import com.edulexa.activity.student.fee.adapter.FeeTypeAdapter
import com.edulexa.databinding.ActivityExamStudentBinding
import com.edulexa.databinding.ActivityFeeStudentBinding

class ExamStudentActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityExamStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpExamList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun setUpExamList(){
        binding!!.recyclerViewExam.layoutManager = GridLayoutManager(mActivity!!,2,
            RecyclerView.VERTICAL,false)
        binding!!.recyclerViewExam.adapter = ExamAdapter(mActivity!!)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            finish()
    }
}