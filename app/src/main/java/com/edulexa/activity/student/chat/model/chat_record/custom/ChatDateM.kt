package com.edulexa.activity.student.chat.model.chat_record.custom

class ChatDateM(date : String) {
    var date = ""
    init {
        this.date = date
    }

    @JvmName("getDate1")
    fun getDate() : String{
        return date
    }
    @JvmName("setDate1")
    fun setDate(date : String){
        this.date = date
    }
}