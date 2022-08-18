package com.edulexa.activity.staff.online_exam.model.subjective.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectiveInfoResponse {
    @SerializedName("exam")
    @Expose
    private var exam: Exam? = null

    @SerializedName("question_result")
    @Expose
    private var questionResult: List<QuestionResult?>? = null

    fun getExam(): Exam? {
        return exam
    }

    fun setExam(exam: Exam?) {
        this.exam = exam
    }

    fun getQuestionResult(): List<QuestionResult?>? {
        if (questionResult == null)
            return ArrayList()
        return questionResult
    }

    fun setQuestionResult(questionResult: List<QuestionResult?>?) {
        this.questionResult = questionResult
    }
}