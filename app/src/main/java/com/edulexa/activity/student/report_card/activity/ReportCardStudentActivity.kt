package com.edulexa.activity.student.report_card.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.report_card.adapter.ReportCardAdapter
import com.edulexa.databinding.ActivityReportCardStudentBinding

class ReportCardStudentActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityReportCardStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportCardStudentBinding.inflate(layoutInflater)
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
        binding!!.recyclerViewReportCard.layoutManager = GridLayoutManager(mActivity!!,2,
            RecyclerView.VERTICAL,false)
        binding!!.recyclerViewReportCard.adapter = ReportCardAdapter(mActivity!!)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            finish()
    }
}