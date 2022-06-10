package com.edulexa.activity.student.attendance.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class StudentAttendanceResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: AttendanceData? = null

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

    fun getData(): AttendanceData? {
        return data
    }

    fun setData(data: AttendanceData?) {
        this.data = data
    }
}