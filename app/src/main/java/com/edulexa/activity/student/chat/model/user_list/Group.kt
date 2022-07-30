package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Group {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("image_url")
    @Expose
    private var imageUrl: Any? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("group_members_id")
    @Expose
    private var groupMembersId: String? = null

    @SerializedName("message")
    @Expose
    private var message: GroupMessage? = null

    @SerializedName("notification_count")
    @Expose
    private var notificationCount: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getTitle(): String? {
        return if (title == null) "null" else title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getImageUrl(): Any? {
        return if (imageUrl == null) "" else imageUrl
    }

    fun setImageUrl(imageUrl: Any?) {
        this.imageUrl = imageUrl
    }

    fun getCreatedBy(): String? {
        return if (createdBy == null) "" else createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getGroupMembersId(): String? {
        return groupMembersId
    }

    fun setGroupMembersId(groupMembersId: String?) {
        this.groupMembersId = groupMembersId
    }

    fun getMessage(): GroupMessage? {
        return message
    }

    fun setMessage(message: GroupMessage?) {
        this.message = message
    }

    fun getNotificationCount(): String? {
        return if (notificationCount == null || notificationCount == "0") "" else notificationCount
    }

    fun setNotificationCount(notificationCount: String?) {
        this.notificationCount = notificationCount
    }
}