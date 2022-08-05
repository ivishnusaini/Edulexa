package com.edulexa.activity.student.chat.model.new_chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddNewChatResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: DataNew? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): DataNew? {
        return data
    }

    fun setData(data: DataNew?) {
        this.data = data
    }
}