package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ChatUserNotification {

    @SerializedName("no_of_notification")
    @Expose
    private var noOfNotification: String? = null

    @SerializedName("chat_connection_id")
    @Expose
    private var chatConnectionId: String? = null

    fun getNoOfNotification(): String? {
        return noOfNotification
    }

    fun setNoOfNotification(noOfNotification: String?) {
        this.noOfNotification = noOfNotification
    }

    fun getChatConnectionId(): String? {
        return chatConnectionId
    }

    fun setChatConnectionId(chatConnectionId: String?) {
        this.chatConnectionId = chatConnectionId
    }
}