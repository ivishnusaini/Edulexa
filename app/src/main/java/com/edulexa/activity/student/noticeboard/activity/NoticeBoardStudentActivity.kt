package com.edulexa.activity.student.noticeboard.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.noticeboard.adapter.NoticeBoardAdapter
import com.edulexa.databinding.ActivityNoticeboardStudentBinding

class NoticeBoardStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityNoticeboardStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeboardStudentBinding.inflate(layoutInflater)
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

    private fun setUpExamList() {
        binding!!.recyclerViewNoticeBoard.layoutManager = LinearLayoutManager(
            mActivity!!,
            RecyclerView.VERTICAL, false
        )
        binding!!.recyclerViewNoticeBoard.adapter = NoticeBoardAdapter(mActivity!!)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            finish()
    }
}