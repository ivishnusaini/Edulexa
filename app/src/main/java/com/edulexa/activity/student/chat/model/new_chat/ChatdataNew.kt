package com.edulexa.activity.student.chat.model.new_chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChatdataNew {
    @SerializedName("chat_user")
    @Expose
    private var chatUser: ChatUserNew? = null

    @SerializedName("chatList")
    @Expose
    private var chatList: List<ChatNew?>? = null

    fun getChatUser(): ChatUserNew? {
        return chatUser
    }

    fun setChatUser(chatUser: ChatUserNew?) {
        this.chatUser = chatUser
    }

    fun getChatList(): List<ChatNew?>? {
        return chatList
    }

    fun setChatList(chatList: List<ChatNew?>?) {
        this.chatList = chatList
    }
}