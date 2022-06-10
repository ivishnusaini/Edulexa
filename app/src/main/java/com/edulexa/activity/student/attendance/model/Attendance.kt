package com.edulexa.activity.student.attendance.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Attendance {
    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("in_time")
    @Expose
    private var inTime: String? = null

    @SerializedName("out_time")
    @Expose
    private var outTime: String? = null

    fun getDate(): String? {
        if (date == null)
            return ""
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getInTime(): String? {
        return inTime
    }

    fun setInTime(inTime: String?) {
        this.inTime = inTime
    }

    fun getOutTime(): String? {
        return outTime
    }

    fun setOutTime(outTime: String?) {
        this.outTime = outTime
    }
}