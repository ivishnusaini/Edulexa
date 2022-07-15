package com.edulexa.activity.student.hostel.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class HostelResponse {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumHostel?>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumHostel?>? {
        return data
    }

    fun setData(data: List<DatumHostel?>?) {
        this.data = data
    }
}