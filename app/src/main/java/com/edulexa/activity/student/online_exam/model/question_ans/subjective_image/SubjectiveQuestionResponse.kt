package com.edulexa.activity.student.online_exam.model.question_ans.subjective_image

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectiveQuestionResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("ids")
    @Expose
    private var ids: List<Int?>? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getMessage(): String? {
        return if (message == null) "Image Added Successfully" else message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getIds(): List<Int?>? {
        return ids
    }

    fun setIds(ids: List<Int?>?) {
        this.ids = ids
    }
}