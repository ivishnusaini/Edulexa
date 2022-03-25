package com.edulexa.activity.student.report_card.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityReportCardDetailBinding
import com.edulexa.databinding.ActivityReportCardStudentBinding

class ReportCardDetailActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityReportCardDetailBinding? = null
    var titleStr: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportCardDetailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            titleStr = bundle!!.getString(Constants.ReportCardDetail.TITLE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpData() {
        binding!!.tvExamDetailTitle.text = titleStr
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}