package com.edulexa.activity.student.chat.model.chat_update

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class UserNewChat {
    @SerializedName("chat_user_id")
    @Expose
    private var chatUserId: String? = null

    @SerializedName("updated_chat")
    @Expose
    private var updatedChat: List<UpdatedChat?>? = null

    fun getChatUserId(): String? {
        return chatUserId
    }

    fun setChatUserId(chatUserId: String?) {
        this.chatUserId = chatUserId
    }

    fun getUpdatedChat(): List<UpdatedChat?>? {
        return updatedChat
    }

    fun setUpdatedChat(updatedChat: List<UpdatedChat?>?) {
        this.updatedChat = updatedChat
    }
}