package com.edulexa.activity.student.chat.model.group

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChatRecordGroupResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: DataGroup? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): DataGroup? {
        return data
    }

    fun setData(data: DataGroup?) {
        this.data = data
    }
}