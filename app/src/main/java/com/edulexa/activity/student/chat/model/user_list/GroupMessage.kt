package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GroupMessage {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("group_member_id")
    @Expose
    private var groupMemberId: String? = null

    @SerializedName("is_first")
    @Expose
    private var isFirst: String? = null

    @SerializedName("is_read")
    @Expose
    private var isRead: String? = null

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
        return if (message == null) "null" else message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getGroupMemberId(): String? {
        return groupMemberId
    }

    fun setGroupMemberId(groupMemberId: String?) {
        this.groupMemberId = groupMemberId
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