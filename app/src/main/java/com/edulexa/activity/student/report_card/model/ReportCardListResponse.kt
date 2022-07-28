package com.edulexa.activity.student.report_card.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ReportCardListResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumReportCardList?>? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): List<DatumReportCardList?>? {
        return data
    }

    fun setData(data: List<DatumReportCardList?>?) {
        this.data = data
    }
}