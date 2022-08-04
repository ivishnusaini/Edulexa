package com.edulexa.activity.student.chat.model.group

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GroupUser {
    @SerializedName("chat_member_id")
    @Expose
    private var chatMemberId: String? = null

    @SerializedName("user_type")
    @Expose
    private var userType: String? = null

    @SerializedName("user_id")
    @Expose
    private var userId: String? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    fun getChatMemberId(): String? {
        return if (chatMemberId == null) "" else chatMemberId
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
        return if (userId == null) "" else userId
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getCreatedBy(): String? {
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getName(): String? {
        return if (name == null) "null" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getImage(): String? {
        return if (image == null) "" else image
    }

    fun setImage(image: String?) {
        this.image = image
    }
}