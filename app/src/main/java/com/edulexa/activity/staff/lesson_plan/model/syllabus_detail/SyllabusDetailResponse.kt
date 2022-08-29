package com.edulexa.activity.staff.lesson_plan.model.syllabus_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SyllabusDetailResponse {
    @SerializedName("data")
    @Expose
    private var data: DataSyllabusDetail? = null

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    fun getData(): DataSyllabusDetail? {
        return data
    }

    fun setData(data: DataSyllabusDetail?) {
        this.data = data
    }

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }
}