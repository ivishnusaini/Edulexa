package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ChatUserListData {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("chat_user_one")
    @Expose
    private var chatUserOne: String? = null

    @SerializedName("chat_user_two")
    @Expose
    private var chatUserTwo: String? = null

    @SerializedName("group_id")
    @Expose
    private var groupId: Any? = null

    @SerializedName("ip")
    @Expose
    private var ip: Any? = null

    @SerializedName("time")
    @Expose
    private var time: Any? = null

    @SerializedName("is_reply")
    @Expose
    private var isReply: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("notification_count")
    @Expose
    private var notificationCount: String? = null

    @SerializedName("messages")
    @Expose
    private var messages: Messages? = null

    @SerializedName("user_details")
    @Expose
    private var userDetails: UserDetails? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getChatUserOne(): String? {
        return chatUserOne
    }

    fun setChatUserOne(chatUserOne: String?) {
        this.chatUserOne = chatUserOne
    }

    fun getChatUserTwo(): String? {
        return chatUserTwo
    }

    fun setChatUserTwo(chatUserTwo: String?) {
        this.chatUserTwo = chatUserTwo
    }

    fun getGroupId(): Any? {
        return groupId
    }

    fun setGroupId(groupId: Any?) {
        this.groupId = groupId
    }

    fun getIp(): Any? {
        return ip
    }

    fun setIp(ip: Any?) {
        this.ip = ip
    }

    fun getTime(): Any? {
        return time
    }

    fun setTime(time: Any?) {
        this.time = time
    }

    fun getIsReply(): String? {
        return isReply
    }

    fun setIsReply(isReply: String?) {
        this.isReply = isReply
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }

    fun getNotificationCount(): String? {
        if (notificationCount == null)
            return ""
        return notificationCount
    }

    fun setNotificationCount(notificationCount: String?) {
        this.notificationCount = notificationCount
    }

    fun getMessages(): Messages? {
        return messages
    }

    fun setMessages(messages: Messages?) {
        this.messages = messages
    }

    fun getUserDetails(): UserDetails? {
        return userDetails
    }

    fun setUserDetails(userDetails: UserDetails?) {
        this.userDetails = userDetails
    }
}