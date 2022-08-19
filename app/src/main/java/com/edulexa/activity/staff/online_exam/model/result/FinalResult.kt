package com.edulexa.activity.staff.online_exam.model.result

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FinalResult {
    @SerializedName("onlineexam_student_id")
    @Expose
    private var onlineexamStudentId: String? = null

    @SerializedName("correct_answer")
    @Expose
    private var correctAnswer: Int? = null

    @SerializedName("incorrect_answer")
    @Expose
    private var incorrectAnswer: Int? = null

    @SerializedName("total_questions")
    @Expose
    private var totalQuestions: String? = null

    @SerializedName("percentage")
    @Expose
    private var percentage = 0.0

    @SerializedName("total_question_mark")
    @Expose
    private var totalQuestionMark: Int? = null

    @SerializedName("obtained_marks")
    @Expose
    private var obtainedMarks: Int? = null

    @SerializedName("student_name")
    @Expose
    private var studentName: String? = null

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    fun getOnlineexamStudentId(): String? {
        return onlineexamStudentId
    }

    fun setOnlineexamStudentId(onlineexamStudentId: String?) {
        this.onlineexamStudentId = onlineexamStudentId
    }

    fun getCorrectAnswer(): Int? {
        return correctAnswer
    }

    fun setCorrectAnswer(correctAnswer: Int?) {
        this.correctAnswer = correctAnswer
    }

    fun getIncorrectAnswer(): Int? {
        return incorrectAnswer
    }

    fun setIncorrectAnswer(incorrectAnswer: Int?) {
        this.incorrectAnswer = incorrectAnswer
    }

    fun getTotalQuestions(): String? {
        return if (totalQuestions == null) "null" else totalQuestions
    }

    fun setTotalQuestions(totalQuestions: String?) {
        this.totalQuestions = totalQuestions
    }

    fun getPercentage(): Double {
        return percentage
    }

    fun setPercentage(percentage: Double) {
        this.percentage = percentage
    }

    fun getTotalQuestionMark(): Int? {
        return if (totalQuestionMark == null) 0 else totalQuestionMark
    }

    fun setTotalQuestionMark(totalQuestionMark: Int?) {
        this.totalQuestionMark = totalQuestionMark
    }

    fun getObtainedMarks(): Int? {
        return if (obtainedMarks == null) 0 else obtainedMarks
    }

    fun setObtainedMarks(obtainedMarks: Int?) {
        this.obtainedMarks = obtainedMarks
    }

    fun getStudentName(): String? {
        return if (studentName == null) "null" else studentName
    }

    fun setStudentName(studentName: String?) {
        this.studentName = studentName
    }

    fun getClass_(): String? {
        return _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getSection(): String? {
        return section
    }

    fun setSection(section: String?) {
        this.section = section
    }
}