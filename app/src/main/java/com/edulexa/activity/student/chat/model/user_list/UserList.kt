package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UserList {
    @SerializedName("chat_users")
    @Expose
    private var chatUsers: List<ChatUserListData?>? = null

    @SerializedName("chat_user_notification")
    @Expose
    private var chatUserNotification: List<ChatUserNotification?>? = null

    fun getChatUsers(): List<ChatUserListData?>? {
        return chatUsers
    }

    fun setChatUsers(chatUsers: List<ChatUserListData?>?) {
        this.chatUsers = chatUsers
    }

    fun getChatUserNotification(): List<ChatUserNotification?>? {
        return chatUserNotification
    }

    fun setChatUserNotification(chatUserNotification: List<ChatUserNotification?>?) {
        this.chatUserNotification = chatUserNotification
    }
}