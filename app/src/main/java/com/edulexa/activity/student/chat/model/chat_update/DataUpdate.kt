package com.edulexa.activity.student.chat.model.chat_update

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataUpdate {
    @SerializedName("user_new_chat")
    @Expose
    private var userNewChat: UserNewChat? = null

    @SerializedName("user_last_chat")
    @Expose
    private var userLastChat: UserLastChat? = null

    fun getUserNewChat(): UserNewChat? {
        return userNewChat
    }

    fun setUserNewChat(userNewChat: UserNewChat?) {
        this.userNewChat = userNewChat
    }

    fun getUserLastChat(): UserLastChat? {
        return userLastChat
    }

    fun setUserLastChat(userLastChat: UserLastChat?) {
        this.userLastChat = userLastChat
    }
}