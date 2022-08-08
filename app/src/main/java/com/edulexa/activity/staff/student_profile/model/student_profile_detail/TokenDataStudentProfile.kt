package com.edulexa.activity.staff.student_profile.model.student_profile_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TokenDataStudentProfile {
    @SerializedName("users_id")
    @Expose
    private var usersId: String? = null

    @SerializedName("token")
    @Expose
    private var token: String? = null

    fun getUsersId(): String? {
        return if (usersId == null) "" else usersId
    }

    fun setUsersId(usersId: String?) {
        this.usersId = usersId
    }

    fun getToken(): String? {
        return if (token == null) "" else token
    }

    fun setToken(token: String?) {
        this.token = token
    }
}