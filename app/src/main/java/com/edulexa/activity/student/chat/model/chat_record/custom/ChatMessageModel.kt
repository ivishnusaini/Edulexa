package com.edulexa.activity.student.chat.model.chat_record.custom

class ChatMessageModel {
    var isFirst = ""
    var chatUserId = ""
    var message = ""
    var time = ""
    var lastId = ""
    var name = ""
    var status = ""

    fun getIsFirst(): String {
        return isFirst
    }

    fun setIsFirst(isFirst: String) {
        this.isFirst = isFirst
    }

    @JvmName("getChatUserId1")
    fun getChatUserId(): String {
        return chatUserId
    }

    @JvmName("setChatUserId1")
    fun setChatUserId(chatUserId: String) {
        this.chatUserId = chatUserId
    }

    @JvmName("getMessage1")
    fun getMessage(): String {
        return message
    }

    @JvmName("setMessage1")
    fun setMessage(message: String) {
        this.message = message
    }

    @JvmName("getTime1")
    fun getTime(): String {
        return time
    }

    @JvmName("setTime1")
    fun setTime(time: String) {
        this.time = time
    }

    @JvmName("getlastId1")
    fun getLastId(): String {
        return lastId
    }

    @JvmName("setlastId1")
    fun setLastId(lastId: String) {
        this.lastId = lastId
    }

    @JvmName("getname1")
    fun getName(): String {
        return name
    }

    @JvmName("setname1")
    fun setName(name: String) {
        this.name = name
    }

    @JvmName("gestatus1")
    fun getStatus(): String {
        return status
    }

    @JvmName("setstatus1")
    fun setStatus(status: String) {
        this.status = status
    }
}