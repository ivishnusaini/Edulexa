package com.edulexa.activity.student.homework.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class StudentHomeworkResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("homework")
    @Expose
    private var homework: List<Homework?>? = null

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

    fun getHomework(): List<Homework?>? {
        return homework
    }

    fun setHomework(homework: List<Homework?>?) {
        this.homework = homework
    }
}