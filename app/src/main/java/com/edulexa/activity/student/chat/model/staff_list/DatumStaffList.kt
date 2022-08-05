package com.edulexa.activity.student.chat.model.staff_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DatumStaffList {
    @SerializedName("staff_id")
    @Expose
    private var staffId: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: Any? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("user_role")
    @Expose
    private var userRole: String? = null

    fun getUserRole(): String? {
        return if (userRole == null) "null" else userRole
    }

    fun setUserRole(userRole: String?) {
        this.userRole = userRole
    }

    fun getStaffId(): String? {
        if (staffId == null)
            return ""
        return staffId
    }

    fun setStaffId(staffId: String?) {
        this.staffId = staffId
    }

    fun getStudentId(): Any? {
        return studentId
    }

    fun setStudentId(studentId: Any?) {
        this.studentId = studentId
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