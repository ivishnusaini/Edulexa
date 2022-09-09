package com.edulexa.activity.staff.school_family.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TeacherListResponse {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumSchoolFamily?>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumSchoolFamily?>? {
        if (data == null)
            return ArrayList()
        return data
    }

    fun setData(data: List<DatumSchoolFamily?>?) {
        this.data = data
    }
}