package com.edulexa.activity.student.chat.model.new_chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserLastChatNew {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("chat_user_id")
    @Expose
    private var chatUserId: String? = null

    @SerializedName("ip")
    @Expose
    private var ip: String? = null

    @SerializedName("time")
    @Expose
    private var time: String? = null

    @SerializedName("is_first")
    @Expose
    private var isFirst: String? = null

    @SerializedName("is_read")
    @Expose
    private var isRead: String? = null

    @SerializedName("chat_connection_id")
    @Expose
    private var chatConnectionId: String? = null

    @SerializedName("group_id")
    @Expose
    private var groupId: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getChatUserId(): String? {
        return chatUserId
    }

    fun setChatUserId(chatUserId: String?) {
        this.chatUserId = chatUserId
    }

    fun getIp(): String? {
        return ip
    }

    fun setIp(ip: String?) {
        this.ip = ip
    }

    fun getTime(): String? {
        return time
    }

    fun setTime(time: String?) {
        this.time = time
    }

    fun getIsFirst(): String? {
        return isFirst
    }

    fun setIsFirst(isFirst: String?) {
        this.isFirst = isFirst
    }

    fun getIsRead(): String? {
        return isRead
    }

    fun setIsRead(isRead: String?) {
        this.isRead = isRead
    }

    fun getChatConnectionId(): String? {
        return chatConnectionId
    }

    fun setChatConnectionId(chatConnectionId: String?) {
        this.chatConnectionId = chatConnectionId
    }

    fun getGroupId(): String? {
        return groupId
    }

    fun setGroupId(groupId: String?) {
        this.groupId = groupId
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }
}