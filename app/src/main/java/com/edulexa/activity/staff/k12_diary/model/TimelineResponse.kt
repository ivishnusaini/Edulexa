package com.edulexa.activity.staff.k12_diary.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimelineResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("timeline_list")
    @Expose
    private var timelineList: List<Timeline?>? = null

    fun getStatus(): Boolean? {
        return if (status == null) false else status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getTimelineList(): List<Timeline?>? {
        return if (timelineList == null) ArrayList() else timelineList
    }

    fun setTimelineList(timelineList: List<Timeline?>?) {
        this.timelineList = timelineList
    }
}