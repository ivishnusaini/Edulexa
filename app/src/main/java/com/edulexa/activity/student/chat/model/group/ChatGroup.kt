package com.edulexa.activity.student.chat.model.group

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChatGroup {
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

    @SerializedName("user_type")
    @Expose
    private var userType: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getMessage(): String? {
        if (message == null)
            return ""
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getGroupMemberId(): String? {
        if (groupMemberId == null)
            return ""
        return groupMemberId
    }

    fun setGroupMemberId(groupMemberId: String?) {
        this.groupMemberId = groupMemberId
    }

    fun getIsFirst(): String? {
        if (isFirst == null)
            return ""
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
        if (createdAt == null)
            return ""
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUserType(): String? {
        return userType
    }

    fun setUserType(userType: String?) {
        this.userType = userType
    }

    fun getName(): String? {
        return if (name == null) "" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }
}