package com.edulexa.activity.student.chat.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.chat.activity.ChatMessageActivity
import com.edulexa.activity.student.chat.activity.StaffListForStudentActivity
import com.edulexa.activity.student.chat.model.staff_list.DatumStaffList
import com.edulexa.activity.student.chat.model.user_list.ChatUserListData
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentChatListBinding
import com.edulexa.databinding.ItemStudentTeacherListBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils

class ChatStaffListAdapter(context: Activity, list : List<DatumStaffList?>?) : RecyclerView.Adapter<ChatStaffListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumStaffList?>?= null
    var binding : ItemStudentTeacherListBinding? = null
    var preference :Preference?= null
    init {
        this.context = context
        this.list = list
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentTeacherListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            preference = Preference().getInstance(context!!)
            binding!!.tvTeacherListName.text = list!!.get(position)!!.getName()
            binding!!.tvTeacherListDesignation.text = context!!.getString(R.string.library_student_string_format,"Role : ",list!![position]!!.getUserRole())

            var baseUrlForImage = preference!!.getString(Constants.Preference.IMAGESURL_STUDENT)
            if (!baseUrlForImage!!.endsWith("/"))
                baseUrlForImage += "/"
            Utils.setpProfileImageUsingGlide(context,baseUrlForImage+list!!.get(position)!!.getImage(),binding!!.ivTeacherListImage)

            viewHolder.itemView.setOnClickListener {
                (context as StaffListForStudentActivity).addStaffForChat(list!![position]!!.getStaffId()!!,list!![position]!!.getName()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentTeacherListBinding) : RecyclerView.ViewHolder(binding.root)
}