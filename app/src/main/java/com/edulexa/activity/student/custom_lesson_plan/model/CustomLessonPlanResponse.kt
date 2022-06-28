package com.edulexa.activity.student.custom_lesson_plan.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class CustomLessonPlanResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumCustomLessonPlan?>? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getData(): List<DatumCustomLessonPlan?>? {
        return data
    }

    fun setData(data: List<DatumCustomLessonPlan?>?) {
        this.data = data
    }
}