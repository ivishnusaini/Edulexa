package com.edulexa.activity.student.online_exam.model.question_ans

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class QuestionAnsResponse {
    @SerializedName("exam")
    @Expose
    private var exam: Exam? = null

    @SerializedName("questionList")
    @Expose
    private var questionList: List<Question?>? = null

    fun getExam(): Exam? {
        return exam
    }

    fun setExam(exam: Exam?) {
        this.exam = exam
    }

    fun getQuestionList(): List<Question?>? {
        return questionList
    }

    fun setQuestionList(questionList: List<Question?>?) {
        this.questionList = questionList
    }
}