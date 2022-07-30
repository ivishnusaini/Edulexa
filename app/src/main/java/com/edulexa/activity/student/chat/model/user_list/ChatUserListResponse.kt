package com.edulexa.activity.student.chat.model.user_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ChatUserListResponse {
    @SerializedName("status")
    @Expose
    private var status: Boolean? = null

    @SerializedName("data")
    @Expose
    private var data: DataChatUserList? = null

    fun getStatus(): Boolean? {
        return status
    }

    fun setStatus(status: Boolean?) {
        this.status = status
    }

    fun getData(): DataChatUserList? {
        return data
    }

    fun setData(data: DataChatUserList?) {
        this.data = data
    }
}