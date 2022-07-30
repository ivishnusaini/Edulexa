package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ChatUser {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("user_type")
    @Expose
    private var userType: String? = null

    @SerializedName("staff_id")
    @Expose
    private var staffId: Any? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null

    @SerializedName("create_staff_id")
    @Expose
    private var createStaffId: String? = null

    @SerializedName("create_student_id")
    @Expose
    private var createStudentId: Any? = null

    @SerializedName("group_id")
    @Expose
    private var groupId: Any? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getUserType(): String? {
        return userType
    }

    fun setUserType(userType: String?) {
        this.userType = userType
    }

    fun getStaffId(): Any? {
        return staffId
    }

    fun setStaffId(staffId: Any?) {
        this.staffId = staffId
    }

    fun getStudentId(): String? {
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }

    fun getCreateStaffId(): String? {
        return createStaffId
    }

    fun setCreateStaffId(createStaffId: String?) {
        this.createStaffId = createStaffId
    }

    fun getCreateStudentId(): Any? {
        return createStudentId
    }

    fun setCreateStudentId(createStudentId: Any?) {
        this.createStudentId = createStudentId
    }

    fun getGroupId(): Any? {
        return groupId
    }

    fun setGroupId(groupId: Any?) {
        this.groupId = groupId
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }
}