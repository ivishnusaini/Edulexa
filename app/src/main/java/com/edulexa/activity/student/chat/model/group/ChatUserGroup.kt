package com.edulexa.activity.student.chat.model.group

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChatUserGroup {
    @SerializedName("chat_member_id")
    @Expose
    private var chatMemberId: String? = null

    @SerializedName("user_type")
    @Expose
    private var userType: String? = null

    @SerializedName("user_id")
    @Expose
    private var userId: String? = null

    @SerializedName("is_deleted")
    @Expose
    private var isDeleted: String? = null

    @SerializedName("deleted_at")
    @Expose
    private var deletedAt: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    fun getChatMemberId(): String? {
        if (chatMemberId == null)
            return ""
        return chatMemberId
    }

    fun setChatMemberId(chatMemberId: String?) {
        this.chatMemberId = chatMemberId
    }

    fun getUserType(): String? {
        return userType
    }

    fun setUserType(userType: String?) {
        this.userType = userType
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getIsDeleted(): String? {
        return isDeleted
    }

    fun setIsDeleted(isDeleted: String?) {
        this.isDeleted = isDeleted
    }

    fun getDeletedAt(): String? {
        return deletedAt
    }

    fun setDeletedAt(deletedAt: String?) {
        this.deletedAt = deletedAt
    }

    fun getName(): String? {
        return name
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