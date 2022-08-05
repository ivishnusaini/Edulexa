package com.edulexa.activity.student.chat.model.new_chat

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataNew {
    @SerializedName("new_user")
    @Expose
    private var newUser: NewUserNew? = null

    @SerializedName("chat_connection_id")
    @Expose
    private var chatConnectionId: Int? = null

    @SerializedName("user_last_chat")
    @Expose
    private var userLastChat: UserLastChatNew? = null

    @SerializedName("chatdata")
    @Expose
    private var chatdata: ChatdataNew? = null

    fun getNewUser(): NewUserNew? {
        return newUser
    }

    fun setNewUser(newUser: NewUserNew?) {
        this.newUser = newUser
    }

    fun getChatConnectionId(): Int? {
        return if (chatConnectionId == null) 0 else chatConnectionId
    }

    fun setChatConnectionId(chatConnectionId: Int?) {
        this.chatConnectionId = chatConnectionId
    }

    fun getUserLastChat(): UserLastChatNew? {
        return userLastChat
    }

    fun setUserLastChat(userLastChat: UserLastChatNew?) {
        this.userLastChat = userLastChat
    }

    fun getChatdata(): ChatdataNew? {
        return chatdata
    }

    fun setChatdata(chatdata: ChatdataNew?) {
        this.chatdata = chatdata
    }
}