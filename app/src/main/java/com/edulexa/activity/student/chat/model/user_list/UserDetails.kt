package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UserDetails {
    @SerializedName("chat_user_id")
    @Expose
    private var chatUserId: String? = null

    @SerializedName("user_type")
    @Expose
    private var userType: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: Any? = null

    @SerializedName("staff_id")
    @Expose
    private var staffId: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("class")
    @Expose
    private var _class: Any? = null

    @SerializedName("section")
    @Expose
    private var section: Any? = null

    @SerializedName("guardian_name")
    @Expose
    private var guardianName: Any? = null

    @SerializedName("staff_role")
    @Expose
    private var staffRole: String? = null

    fun getChatUserId(): String? {
        if (chatUserId == null)
            return ""
        return chatUserId
    }

    fun setChatUserId(chatUserId: String?) {
        this.chatUserId = chatUserId
    }

    fun getUserType(): String? {
        return userType
    }

    fun setUserType(userType: String?) {
        this.userType = userType
    }

    fun getStudentId(): Any? {
        return studentId
    }

    fun setStudentId(studentId: Any?) {
        this.studentId = studentId
    }

    fun getStaffId(): String? {
        return staffId
    }

    fun setStaffId(staffId: String?) {
        this.staffId = staffId
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getImage(): String? {
        if (image == null)
            return ""
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getClass_(): Any? {
        return _class
    }

    fun setClass_(_class: Any?) {
        this._class = _class
    }

    fun getSection(): Any? {
        return section
    }

    fun setSection(section: Any?) {
        this.section = section
    }

    fun getGuardianName(): Any? {
        return guardianName
    }

    fun setGuardianName(guardianName: Any?) {
        this.guardianName = guardianName
    }

    fun getStaffRole(): String? {
        return staffRole
    }

    fun setStaffRole(staffRole: String?) {
        this.staffRole = staffRole
    }
}