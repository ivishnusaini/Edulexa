package com.edulexa.activity.student.homework.model.subject_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class SubjectListResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: List<SubjectListDatum?>? = null

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

    fun getData(): List<SubjectListDatum?>? {
        return data
    }

    fun setData(data: List<SubjectListDatum?>?) {
        this.data = data
    }

}