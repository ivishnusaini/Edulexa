package com.edulexa.activity.student.chat.model.chat_update

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UpdatedChat {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("chat_member_id")
    @Expose
    private var chatMemberId: String? = null

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

    @SerializedName("name")
    @Expose
    private var name: String? = null

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

    @SerializedName("group_member_id")
    @Expose
    private var groupMemberId: String? = null

    @SerializedName("is_file")
    @Expose
    private var isFile: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getChatMemberId(): String? {
        if (chatMemberId == null)
            return ""
        return chatMemberId
    }

    fun setChatMemberId(chatMemberId: String?) {
        this.chatMemberId = chatMemberId
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

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
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

    fun getGroupMemberId(): String? {
        return groupMemberId
    }

    fun setGroupMemberId(groupMemberId: String?) {
        this.groupMemberId = groupMemberId
    }

    fun getIsFile(): String? {
        return isFile
    }

    fun setIsFile(isFile: String?) {
        this.isFile = isFile
    }

    fun getCreatedAt(): String? {
        if (createdAt == null)
            return ""
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }
}