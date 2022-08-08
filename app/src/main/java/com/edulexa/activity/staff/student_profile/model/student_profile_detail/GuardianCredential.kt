package com.edulexa.activity.staff.student_profile.model.student_profile_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GuardianCredential {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("user_id")
    @Expose
    private var userId: String? = null

    @SerializedName("username")
    @Expose
    private var username: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getUserId(): String? {
        return userId
    }

    fun setUserId(userId: String?) {
        this.userId = userId
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }
}