package com.edulexa.activity.student.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ParentChildList {
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null
    @SerializedName("image")
    @Expose
    private var image: String? = null
    @SerializedName("class")
    @Expose
    private var classStudent: String? = null
    @SerializedName("section")
    @Expose
    private var section: String? = null
    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
    fun getStudentId(): String? {
        if (studentId == null)
            return ""
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }
    fun getImage(): String? {
        if (image == null)
            return ""
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }
    fun getClass(): String? {
        if (classStudent == null)
            return ""
        return classStudent
    }

    fun setClass(classStudent: String?) {
        this.classStudent = classStudent
    }
    fun getSection(): String? {
        if (section == null)
            return ""
        return section
    }

    fun setSection(section: String?) {
        this.section = section
    }
    fun getStudentSessionId(): String? {
        if (studentSessionId == null)
            return ""
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }
}