package com.edulexa.activity.student.chat.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.chat.adapter.GroupInfoAdapter
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityChatMessageGroupStudentBinding
import com.edulexa.databinding.ActivityGroupInfoStudentBinding

class GroupInfoActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityGroupInfoStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupInfoStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init(){
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
            val titleStr = bundle!!.getString(Constants.StudentChat.CHAT_NAME)!!
            binding!!.tvTitle.text = titleStr
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpData(){
        binding!!.recyclerView.layoutManager = LinearLayoutManager(mActivity!!,RecyclerView.VERTICAL,false)
        binding!!.recyclerView.adapter = GroupInfoAdapter(mActivity!!,Constants.AppSaveData.listGroupUsers)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}