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
import com.edulexa.activity.student.chat.activity.ChatMessageGroupActivity
import com.edulexa.activity.student.chat.model.user_list.Group
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentChatListBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils

class ChatListGroupAdapter(context: Activity, list : List<Group?>?) : RecyclerView.Adapter<ChatListGroupAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<Group?>?= null
    var binding : ItemStudentChatListBinding? = null
    var preference :Preference?= null
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
            preference = Preference().getInstance(context!!)
            binding!!.tvUserName.text = list!!.get(position)!!.getTitle()
            binding!!.tvMessage.text = list!!.get(position)!!.getMessage()!!.getMessage()
            if (list!!.get(position)!!.getNotificationCount().equals(""))
                binding!!.counterLay.visibility = View.GONE
            else {
                binding!!.counterLay.visibility = View.VISIBLE
                binding!!.tvMessageCount.text = list!!.get(position)!!.getNotificationCount()
            }
            if (!list!!.get(position)!!.getImageUrl()!!.equals("")){
                var baseUrlForImage = preference!!.getString(Constants.Preference.IMAGESURL_STUDENT)
                if (!baseUrlForImage!!.endsWith("/"))
                    baseUrlForImage = baseUrlForImage + "/"
                Utils.setpProfileImageUsingGlide(context,baseUrlForImage+list!!.get(position)!!.getImageUrl(),binding!!.ivImage)
            }else binding!!.ivImage.setBackgroundResource(R.drawable.group_student)


            viewHolder.itemView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val bundle = Bundle()
                    bundle.putString(Constants.StudentChat.CHAT_NAME,list!!.get(position)!!.getTitle())
                    bundle.putString(Constants.StudentChat.GROUP_ID,list!!.get(position)!!.getId())
                    context!!.startActivity(Intent(context, ChatMessageGroupActivity::class.java).putExtras(bundle))
                }

            })

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentChatListBinding) : RecyclerView.ViewHolder(binding.root)
}