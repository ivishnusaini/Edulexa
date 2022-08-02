package com.edulexa.activity.student.chat.model.chat_record

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ChatRecordResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: DataChatRecord? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): DataChatRecord? {
        return data
    }

    fun setData(data: DataChatRecord?) {
        this.data = data
    }
}