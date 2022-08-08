package com.edulexa.activity.staff.student_profile.model.student_profile_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StudentViewResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: DataStudentProfile? = null

    fun getStatus(): Int? {
        return if (status == null) 0 else status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): DataStudentProfile? {
        return data
    }

    fun setData(data: DataStudentProfile?) {
        this.data = data
    }
}