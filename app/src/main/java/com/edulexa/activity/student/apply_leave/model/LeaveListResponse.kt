package com.edulexa.activity.student.apply_leave.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class LeaveListResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: DataApplyLeave? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): DataApplyLeave? {
        return data
    }

    fun setData(data: DataApplyLeave?) {
        this.data = data
    }
}