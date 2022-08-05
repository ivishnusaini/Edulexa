package com.edulexa.activity.student.chat.model.new_chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewUserNew {
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
    private var staffId: Any? = null

    @SerializedName("name")
    @Expose
    private var name: Any? = null

    @SerializedName("image")
    @Expose
    private var image: Any? = null

    fun getChatUserId(): String? {
        return if (chatUserId == null) "" else chatUserId
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

    fun getStaffId(): Any? {
        return staffId
    }

    fun setStaffId(staffId: Any?) {
        this.staffId = staffId
    }

    fun getName(): Any? {
        return name
    }

    fun setName(name: Any?) {
        this.name = name
    }

    fun getImage(): Any? {
        return image
    }

    fun setImage(image: Any?) {
        this.image = image
    }
}