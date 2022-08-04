package com.edulexa.activity.student.chat.model.group

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataGroup {
    @SerializedName("group_users")
    @Expose
    private var groupUsers: List<GroupUser?>? = null

    @SerializedName("chat_user")
    @Expose
    private var chatUser: ChatUserGroup? = null

    @SerializedName("chatList")
    @Expose
    private var chatList: List<ChatGroup?>? = null

    fun getGroupUsers(): List<GroupUser?>? {
        return groupUsers
    }

    fun setGroupUsers(groupUsers: List<GroupUser?>?) {
        this.groupUsers = groupUsers
    }

    fun getChatUser(): ChatUserGroup? {
        return chatUser
    }

    fun setChatUser(chatUser: ChatUserGroup?) {
        this.chatUser = chatUser
    }

    fun getChatList(): List<ChatGroup?>? {
        return chatList
    }

    fun setChatList(chatList: List<ChatGroup?>?) {
        this.chatList = chatList
    }
}