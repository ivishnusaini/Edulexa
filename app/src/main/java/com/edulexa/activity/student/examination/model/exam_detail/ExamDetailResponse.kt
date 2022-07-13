package com.edulexa.activity.student.examination.model.exam_detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ExamDetailResponse {
    @SerializedName("data")
    @Expose
    private var data: List<DatumExamDetail?>? = null

    @SerializedName("success")
    @Expose
    private var success: Int? = null

    fun getData(): List<DatumExamDetail?>? {
        return data
    }

    fun setData(data: List<DatumExamDetail?>?) {
        this.data = data
    }

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }
}