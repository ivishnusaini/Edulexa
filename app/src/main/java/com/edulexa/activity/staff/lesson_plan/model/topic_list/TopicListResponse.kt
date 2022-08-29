package com.edulexa.activity.staff.lesson_plan.model.topic_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TopicListResponse {
    @SerializedName("data")
    @Expose
    private var data: List<DatumTopic?>? = null

    fun getData(): List<DatumTopic?>? {
        if (data == null)
            return ArrayList()
        return data
    }

    fun setData(data: List<DatumTopic?>?) {
        this.data = data
    }
}