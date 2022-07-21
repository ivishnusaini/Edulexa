package com.edulexa.activity.student.online_exam.model.question_ans

class UserAnsModel(questionId : String,questionAns : String) {
    var questionId = ""
    var questionAns = ""
    init {
        this.questionId = questionId
        this.questionAns = questionAns
    }

    @JvmName("getQuestionId1")
    fun getQuestionId() : String{
        return questionId
    }

    @JvmName("setQuestionId1")
    fun setQuestionId(questionId : String){
        this.questionId = questionId
    }

    @JvmName("getquestionAns1")
    fun getQuestionAns() : String{
        return questionAns
    }

    @JvmName("setquestionAns1")
    fun setQuestionAns(questionAns : String){
        this.questionAns = questionAns
    }
}