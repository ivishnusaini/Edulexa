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
import com.edulexa.activity.student.examination.adapter.ExamDetailAdapter
import com.edulexa.databinding.ActivityExamDetailBinding
import com.edulexa.databinding.ActivityExamStudentBinding

class ExamDetailActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityExamDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExamDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpExamDetailList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    private fun setUpExamDetailList(){
        binding!!.recyclerViewExamDetail.layoutManager = LinearLayoutManager(mActivity!!,
            RecyclerView.VERTICAL,false)
        binding!!.recyclerViewExamDetail.adapter = ExamDetailAdapter(mActivity!!)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}