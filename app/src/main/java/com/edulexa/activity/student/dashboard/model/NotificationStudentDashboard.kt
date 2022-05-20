package com.edulexa.activity.student.dashboard.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class NotificationStudentDashboard {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumNotification?>? = null

    fun getSuccess(): Int? {
        if (success == null)
            return 0
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumNotification?>? {
        return data
    }

    fun setData(data: List<DatumNotification?>?) {
        this.data = data
    }
}