package com.edulexa.activity.student.calendar.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class CalendarListResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumCalendar?>? = null

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

    fun getData(): List<DatumCalendar?>? {
        return data
    }

    fun setData(data: List<DatumCalendar?>?) {
        this.data = data
    }
}