package com.edulexa.activity.staff.student_profile.model.student_profile_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReasonStudentProfile {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("reason")
    @Expose
    private var reason: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getReason(): String? {
        return reason
    }

    fun setReason(reason: String?) {
        this.reason = reason
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }
}