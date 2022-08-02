package com.edulexa.activity.student.chat.model.chat_update

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ChatUpdateResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: DataUpdate? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): DataUpdate? {
        return data
    }

    fun setData(data: DataUpdate?) {
        this.data = data
    }
}