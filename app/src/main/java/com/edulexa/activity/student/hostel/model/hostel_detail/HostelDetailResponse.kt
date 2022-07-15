package com.edulexa.activity.student.hostel.model.hostel_detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class HostelDetailResponse {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumHostelDetail?>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumHostelDetail?>? {
        return data
    }

    fun setData(data: List<DatumHostelDetail?>?) {
        this.data = data
    }
}