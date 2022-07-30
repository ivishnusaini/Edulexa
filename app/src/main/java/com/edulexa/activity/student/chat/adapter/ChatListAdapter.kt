package com.edulexa.activity.student.chat.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.chat.model.user_list.ChatUserListData
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentChatListBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils

class ChatListAdapter(context: Activity, list : List<ChatUserListData?>?,chatUserId : String) : RecyclerView.Adapter<ChatListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<ChatUserListData?>?= null
    var binding : ItemStudentChatListBinding? = null
    var chatUserId : String? = null
    var preference :Preference?= null
    init {
        this.context = context
        this.list = list
        this.chatUserId = chatUserId
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            preference = Preference().getInstance(context!!)
            binding!!.tvUserName.text = list!!.get(position)!!.getUserDetails()!!.getName()
            binding!!.tvMessage.text = list!!.get(position)!!.getMessages()!!.getMessage()
            if (list!!.get(position)!!.getNotificationCount().equals(""))
                binding!!.counterLay.visibility = View.GONE
            else {
                binding!!.counterLay.visibility = View.VISIBLE
                binding!!.tvMessageCount.text = list!!.get(position)!!.getNotificationCount()
            }
            var baseUrlForImage = preference!!.getString(Constants.Preference.IMAGESURL_STUDENT)
            if (!baseUrlForImage!!.endsWith("/"))
                baseUrlForImage = baseUrlForImage + "/"
            Utils.setpProfileImageUsingGlide(context,baseUrlForImage+list!!.get(position)!!.getUserDetails()!!.getImage(),binding!!.ivImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentChatListBinding) : RecyclerView.ViewHolder(binding.root)
}