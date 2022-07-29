package com.edulexa.activity.student.chat.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.edulexa.R
import com.edulexa.databinding.ActivityChatListStudentBinding
import com.edulexa.databinding.ActivityReportCardStudentBinding

class ChatListActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityChatListStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}