package com.edulexa.activity.student.chat.model.staff_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StaffListResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumStaffList?>? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): List<DatumStaffList?>? {
        return data
    }

    fun setData(data: List<DatumStaffList?>?) {
        this.data = data
    }
}