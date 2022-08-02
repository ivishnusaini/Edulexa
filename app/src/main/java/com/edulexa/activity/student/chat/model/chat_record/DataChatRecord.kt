package com.edulexa.activity.student.chat.model.chat_record

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DataChatRecord {
    @SerializedName("chat_user")
    @Expose
    private var chatUser: ChatUserChatRecord? = null

    @SerializedName("chatList")
    @Expose
    private var chatList: List<ChatRecord?>? = null

    fun getChatUser(): ChatUserChatRecord? {
        return chatUser
    }

    fun setChatUser(chatUser: ChatUserChatRecord?) {
        this.chatUser = chatUser
    }

    fun getChatList(): List<ChatRecord?>? {
        return chatList
    }

    fun setChatList(chatList: List<ChatRecord?>?) {
        this.chatList = chatList
    }
}