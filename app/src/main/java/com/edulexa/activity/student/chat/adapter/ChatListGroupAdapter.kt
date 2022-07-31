package com.edulexa.activity.student.chat.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.chat.model.user_list.Group
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.databinding.ItemStudentChatListBinding

class ChatListGroupAdapter(context: Activity, list : List<Group?>?) : RecyclerView.Adapter<ChatListGroupAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<Group?>?= null
    var binding : ItemStudentChatListBinding? = null
    init {
        this.context = context
        this.list = list
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

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentChatListBinding) : RecyclerView.ViewHolder(binding.root)
}