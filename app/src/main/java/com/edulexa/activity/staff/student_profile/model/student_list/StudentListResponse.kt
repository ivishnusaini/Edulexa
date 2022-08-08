package com.edulexa.activity.staff.student_profile.model.student_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StudentListResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("student_list")
    @Expose
    private var studentList: List<StudentDatum?>? = null

    fun getStatus(): Int? {
        return if (status == null) 0 else status
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

    fun getStudentList(): List<StudentDatum?>? {
        return if (studentList == null) ArrayList<StudentDatum?>() else studentList
    }

    fun setStudentList(studentList: List<StudentDatum?>?) {
        this.studentList = studentList
    }
}