package com.edulexa.activity.student.chat.model.group.send_message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SendMessageGroupResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: DataSendMessageGroup? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): DataSendMessageGroup? {
        return data
    }

    fun setData(data: DataSendMessageGroup?) {
        this.data = data
    }
}