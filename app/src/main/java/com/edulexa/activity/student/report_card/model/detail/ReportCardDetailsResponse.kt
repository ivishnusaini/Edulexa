package com.edulexa.activity.student.report_card.model.detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ReportCardDetailsResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("total_marks")
    @Expose
    private var totalMarks: Int? = null

    @SerializedName("get_marks")
    @Expose
    private var getMarks: Int? = null

    @SerializedName("percentage")
    @Expose
    private var percentage: String? = null

    @SerializedName("grade")
    @Expose
    private var grade: String? = null

    @SerializedName("result")
    @Expose
    private var result: String? = null

    @SerializedName("exam_result")
    @Expose
    private var examResult: List<ExamResult?>? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getTotalMarks(): Int? {
        if (totalMarks == null)
            return 0
        return totalMarks
    }

    fun setTotalMarks(totalMarks: Int?) {
        this.totalMarks = totalMarks
    }

    fun getGetMarks(): Int? {
        if (getMarks == null)
            return 0
        return getMarks
    }

    fun setGetMarks(getMarks: Int?) {
        this.getMarks = getMarks
    }

    fun getPercentage(): String? {
        if (percentage == null)
            return "0%"
        return percentage
    }

    fun setPercentage(percentage: String?) {
        this.percentage = percentage
    }

    fun getGrade(): String? {
        if (grade == null || grade.equals("no record found") || grade.equals("empty list"))
            return "NA"
        return grade
    }

    fun setGrade(grade: String?) {
        this.grade = grade
    }

    fun getResult(): String? {
        return result
    }

    fun setResult(result: String?) {
        this.result = result
    }

    fun getExamResult(): List<ExamResult?>? {
        return examResult
    }

    fun setExamResult(examResult: List<ExamResult?>?) {
        this.examResult = examResult
    }
}