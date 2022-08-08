package com.edulexa.activity.staff.student_profile.model.class_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClassResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("classes")
    @Expose
    private var classes: List<ClassData?>? = null

    @SerializedName("staff_id")
    @Expose
    private var staffId: String? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getClasses(): List<ClassData?>? {
        return classes
    }

    fun setClasses(classes: List<ClassData?>?) {
        this.classes = classes
    }

    fun getStaffId(): String? {
        return staffId
    }

    fun setStaffId(staffId: String?) {
        this.staffId = staffId
    }
}