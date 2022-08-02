package com.edulexa.activity.student.chat.model.send_message

import com.edulexa.activity.student.chat.model.chat_update.UserLastChat

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataSendMessage {
    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("last_insert_id")
    @Expose
    private var lastInsertId: Int? = null

    @SerializedName("user_last_chat")
    @Expose
    private var userLastChat: UserLastChat? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getLastInsertId(): Int? {
        if (lastInsertId == null)
            return 0
        return lastInsertId
    }

    fun setLastInsertId(lastInsertId: Int?) {
        this.lastInsertId = lastInsertId
    }

    fun getUserLastChat(): UserLastChat? {
        return userLastChat
    }

    fun setUserLastChat(userLastChat: UserLastChat?) {
        this.userLastChat = userLastChat
    }
}