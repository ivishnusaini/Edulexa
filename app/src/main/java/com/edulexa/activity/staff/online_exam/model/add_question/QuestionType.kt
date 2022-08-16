package com.edulexa.activity.staff.online_exam.model.add_question

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class QuestionType {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}