package com.edulexa.activity.staff.online_exam.model.examwise_questions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ExamwiseQuestionsResponse {
    @SerializedName("questions")
    @Expose
    private var questions: List<QuestionExamWise?>? = null

    fun getQuestions(): List<QuestionExamWise?>? {
        if (questions == null)
            return ArrayList()
        return questions
    }

    fun setQuestions(questions: List<QuestionExamWise?>?) {
        this.questions = questions
    }
}