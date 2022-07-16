package com.edulexa.activity.student.online_exam.model.exam_list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class OnlineExamListResponse {
    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("online_exam_native")
    @Expose
    private var onlineExamNative: String? = null

    @SerializedName("webview_url")
    @Expose
    private var webviewUrl: String? = null

    @SerializedName("examSchedule")
    @Expose
    private var examSchedule: List<ExamSchedule?>? = null

    fun getStudentSessionId(): String? {
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }

    fun getOnlineExamNative(): String? {
        if (onlineExamNative == null)
            return ""
        return onlineExamNative
    }

    fun setOnlineExamNative(onlineExamNative: String?) {
        this.onlineExamNative = onlineExamNative
    }

    fun getWebviewUrl(): String? {
        if (webviewUrl == null)
            return ""
        return webviewUrl
    }

    fun setWebviewUrl(webviewUrl: String?) {
        this.webviewUrl = webviewUrl
    }

    fun getExamSchedule(): List<ExamSchedule?>? {
        return examSchedule
    }

    fun setExamSchedule(examSchedule: List<ExamSchedule?>?) {
        this.examSchedule = examSchedule
    }
}