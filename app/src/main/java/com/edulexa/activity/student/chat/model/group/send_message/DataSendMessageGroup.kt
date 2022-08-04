package com.edulexa.activity.student.chat.model.group.send_message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataSendMessageGroup {
    @SerializedName("user_new_chat")
    @Expose
    private var userNewChat: UserNewChatGroupSendMessage? = null

    @SerializedName("user_last_chat")
    @Expose
    private var userLastChat: UserLastChatGroupSendMessage? = null

    fun getUserNewChat(): UserNewChatGroupSendMessage? {
        return userNewChat
    }

    fun setUserNewChat(userNewChat: UserNewChatGroupSendMessage?) {
        this.userNewChat = userNewChat
    }

    fun getUserLastChat(): UserLastChatGroupSendMessage? {
        return userLastChat
    }

    fun setUserLastChat(userLastChat: UserLastChatGroupSendMessage?) {
        this.userLastChat = userLastChat
    }
}