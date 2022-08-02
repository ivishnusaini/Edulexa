package com.edulexa.activity.student.chat.model.send_message

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class SendMessageResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: DataSendMessage? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): DataSendMessage? {
        return data
    }

    fun setData(data: DataSendMessage?) {
        this.data = data
    }
}