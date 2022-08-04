package com.edulexa.activity.student.chat.model.group.send_message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserNewChatGroupSendMessage {
    @SerializedName("chat_user_id")
    @Expose
    private var chatUserId: String? = null

    @SerializedName("chatList")
    @Expose
    private var chatList: List<Any?>? = null

    fun getChatUserId(): String? {
        return chatUserId
    }

    fun setChatUserId(chatUserId: String?) {
        this.chatUserId = chatUserId
    }

    fun getChatList(): List<Any?>? {
        return chatList
    }

    fun setChatList(chatList: List<Any?>?) {
        this.chatList = chatList
    }
}