package com.edulexa.activity.student.report_card.model.detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ExamResult {
    @SerializedName("exam_schedule_id")
    @Expose
    private var examScheduleId: String? = null

    @SerializedName("exam_id")
    @Expose
    private var examId: String? = null

    @SerializedName("full_marks")
    @Expose
    private var fullMarks: String? = null

    @SerializedName("passing_marks")
    @Expose
    private var passingMarks: String? = null

    @SerializedName("exam_name")
    @Expose
    private var examName: String? = null

    @SerializedName("attendence")
    @Expose
    private var attendence: String? = null

    @SerializedName("get_marks")
    @Expose
    private var getMarks: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    fun getExamScheduleId(): String? {
        return examScheduleId
    }

    fun setExamScheduleId(examScheduleId: String?) {
        this.examScheduleId = examScheduleId
    }

    fun getExamId(): String? {
        return examId
    }

    fun setExamId(examId: String?) {
        this.examId = examId
    }

    fun getFullMarks(): String? {
        return fullMarks
    }

    fun setFullMarks(fullMarks: String?) {
        this.fullMarks = fullMarks
    }

    fun getPassingMarks(): String? {
        return passingMarks
    }

    fun setPassingMarks(passingMarks: String?) {
        this.passingMarks = passingMarks
    }

    fun getExamName(): String? {
        return examName
    }

    fun setExamName(examName: String?) {
        this.examName = examName
    }

    fun getAttendence(): String? {
        return attendence
    }

    fun setAttendence(attendence: String?) {
        this.attendence = attendence
    }

    fun getGetMarks(): String? {
        return getMarks
    }

    fun setGetMarks(getMarks: String?) {
        this.getMarks = getMarks
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }
}