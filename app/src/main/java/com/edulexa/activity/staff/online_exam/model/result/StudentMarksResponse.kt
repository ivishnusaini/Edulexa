package com.edulexa.activity.staff.online_exam.model.result

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StudentMarksResponse {
    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("final_result")
    @Expose
    private var finalResult: List<FinalResult?>? = null

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getFinalResult(): List<FinalResult?>? {
        if (finalResult == null)
            return ArrayList()
        return finalResult
    }

    fun setFinalResult(finalResult: List<FinalResult?>?) {
        this.finalResult = finalResult
    }
}