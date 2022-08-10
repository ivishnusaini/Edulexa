package com.edulexa.activity.staff.dashboard.model.notifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Notification {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumNotification?>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumNotification?>? {
        if (data == null)
            return ArrayList()
        return data
    }

    fun setData(data: List<DatumNotification?>?) {
        this.data = data
    }
}