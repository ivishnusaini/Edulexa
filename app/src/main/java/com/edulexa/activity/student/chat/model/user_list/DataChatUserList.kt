package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataChatUserList {
    @SerializedName("chat_user")
    @Expose
    private var chatUser: ChatUser? = null

    @SerializedName("userList")
    @Expose
    private var userList: UserList? = null

    @SerializedName("groupList")
    @Expose
    private var groupList: List<Group?>? = null

    fun getChatUser(): ChatUser? {
        return chatUser
    }

    fun setChatUser(chatUser: ChatUser?) {
        this.chatUser = chatUser
    }

    fun getUserList(): UserList? {
        return userList
    }

    fun setUserList(userList: UserList?) {
        this.userList = userList
    }

    fun getGroupList(): List<Group?>? {
        return groupList
    }

    fun setGroupList(groupList: List<Group?>?) {
        this.groupList = groupList
    }
}