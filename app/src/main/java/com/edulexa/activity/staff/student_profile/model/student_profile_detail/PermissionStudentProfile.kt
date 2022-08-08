package com.edulexa.activity.staff.student_profile.model.student_profile_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PermissionStudentProfile {
    @SerializedName("student_view")
    @Expose
    private var studentView: Boolean? = null

    @SerializedName("student_edit")
    @Expose
    private var studentEdit: Boolean? = null

    @SerializedName("student_add")
    @Expose
    private var studentAdd: Boolean? = null

    @SerializedName("student_delete")
    @Expose
    private var studentDelete: Boolean? = null

    @SerializedName("student_fees_view")
    @Expose
    private var studentFeesView: Boolean? = null

    @SerializedName("student_timeline_view")
    @Expose
    private var studentTimelineView: Boolean? = null

    @SerializedName("student_disable")
    @Expose
    private var studentDisable: Boolean? = null

    @SerializedName("student_login_credential_report")
    @Expose
    private var studentLoginCredentialReport: Boolean? = null

    @SerializedName("student_attendance_view")
    @Expose
    private var studentAttendanceView: Boolean? = null

    @SerializedName("student_exam_result_view")
    @Expose
    private var studentExamResultView: Boolean? = null

    fun getStudentView(): Boolean? {
        return studentView
    }

    fun setStudentView(studentView: Boolean?) {
        this.studentView = studentView
    }

    fun getStudentEdit(): Boolean? {
        return studentEdit
    }

    fun setStudentEdit(studentEdit: Boolean?) {
        this.studentEdit = studentEdit
    }

    fun getStudentAdd(): Boolean? {
        return studentAdd
    }

    fun setStudentAdd(studentAdd: Boolean?) {
        this.studentAdd = studentAdd
    }

    fun getStudentDelete(): Boolean? {
        return studentDelete
    }

    fun setStudentDelete(studentDelete: Boolean?) {
        this.studentDelete = studentDelete
    }

    fun getStudentFeesView(): Boolean? {
        return studentFeesView
    }

    fun setStudentFeesView(studentFeesView: Boolean?) {
        this.studentFeesView = studentFeesView
    }

    fun getStudentTimelineView(): Boolean? {
        return studentTimelineView
    }

    fun setStudentTimelineView(studentTimelineView: Boolean?) {
        this.studentTimelineView = studentTimelineView
    }

    fun getStudentDisable(): Boolean? {
        return studentDisable
    }

    fun setStudentDisable(studentDisable: Boolean?) {
        this.studentDisable = studentDisable
    }

    fun getStudentLoginCredentialReport(): Boolean? {
        return studentLoginCredentialReport
    }

    fun setStudentLoginCredentialReport(studentLoginCredentialReport: Boolean?) {
        this.studentLoginCredentialReport = studentLoginCredentialReport
    }

    fun getStudentAttendanceView(): Boolean? {
        return studentAttendanceView
    }

    fun setStudentAttendanceView(studentAttendanceView: Boolean?) {
        this.studentAttendanceView = studentAttendanceView
    }

    fun getStudentExamResultView(): Boolean? {
        return studentExamResultView
    }

    fun setStudentExamResultView(studentExamResultView: Boolean?) {
        this.studentExamResultView = studentExamResultView
    }
}