package com.edulexa.activity.student.teacher_rating.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class TeacherListResponse {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumTeacherList?>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumTeacherList?>? {
        return data
    }

    fun setData(data: List<DatumTeacherList?>?) {
        this.data = data
    }
}