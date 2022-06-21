package com.edulexa.activity.student.dashboard.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class StudentDashboardResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: DashboardDatum? = null


    fun getStatus(): Int? {
        return status
    }

    fun setHomeworklist(status: Int) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getDashboardDatum(): DashboardDatum? {
        return data
    }

    fun setDashboardDatum(data: DashboardDatum) {
        this.data = data
    }
}