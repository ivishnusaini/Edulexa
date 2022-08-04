package com.edulexa.activity.student.chat.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.chat.model.chat_record.custom.ChatDateM
import com.edulexa.activity.student.chat.model.chat_record.custom.ChatMessageModel
import com.edulexa.databinding.ItemChatMessageBinding
import com.edulexa.databinding.ItemChatMessageDateBinding
import com.edulexa.support.Preference
import java.text.SimpleDateFormat
import java.util.*

class ChatMessageGroupAdapter(context: Activity, list : List<Any?>?, chatId : String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context: Activity? = null
    var list : List<Any?>?= null
    var chatMessageBinding : ItemChatMessageBinding? = null
    var chatDateBinding : ItemChatMessageDateBinding? = null
    var chatId : String? = null
    var preference :Preference?= null
    init {
        this.context = context
        this.list = list
        this.chatId = chatId
    }

    override fun getItemViewType(position: Int): Int {
        if (list!!.get(position) is ChatMessageModel)
            return 1
        else return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            chatMessageBinding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(chatMessageBinding!!)
        }
        chatDateBinding = ItemChatMessageDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolderDate(chatDateBinding!!)
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            preference = Preference().getInstance(context!!)
            when(getItemViewType(position)){
                1 -> {
                    val model = list!![position] as ChatMessageModel
                    if (!model.getIsFirst().equals("1")){
                        if (!chatId.equals(model.getChatUserId())){
                            chatMessageBinding!!.tvUserName.visibility = View.VISIBLE
                            chatMessageBinding!!.messageOtherLay.visibility = View.VISIBLE
                            chatMessageBinding!!.messageMeLay.visibility = View.GONE
                        }else{
                            chatMessageBinding!!.messageOtherLay.visibility = View.GONE
                            chatMessageBinding!!.messageMeLay.visibility = View.VISIBLE
                        }
                        if (model.getName().equals(""))
                            chatMessageBinding!!.tvUserName.visibility = View.GONE
                        else chatMessageBinding!!.tvUserName.visibility = View.VISIBLE

                        chatMessageBinding!!.tvUserName.text = model.getName()
                        chatMessageBinding!!.tvMessage.text = model.getMessage()
                        chatMessageBinding!!.tvMessageMe.text = model.getMessage()
                        chatMessageBinding!!.tvTime.text = getFormattedTime(model.getTime())
                        chatMessageBinding!!.tvTimeMessageMe.text = getFormattedTime(model.getTime())
                    }
                }
                else -> {
                    val model = list!![position] as ChatDateM
                    chatDateBinding!!.tvDate.text = model.getDate()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getFormattedTime(time: String): String? {
        if (time != "") {
            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            @SuppressLint("SimpleDateFormat") val format2 = SimpleDateFormat("HH:mm a")
            val date: Date
            return try {
                date = format.parse(time)
                format2.format(Objects.requireNonNull(date))
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                ""
            }
        }
        return ""
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class MyViewHolder(binding: ItemChatMessageBinding) : RecyclerView.ViewHolder(binding.root)

    class MyViewHolderDate(binding: ItemChatMessageDateBinding) : RecyclerView.ViewHolder(binding.root)

}