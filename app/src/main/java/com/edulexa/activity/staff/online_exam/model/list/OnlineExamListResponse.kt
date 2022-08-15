package com.edulexa.activity.staff.online_exam.model.list

import com.edulexa.activity.student.online_exam.model.question_ans.Exam

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class OnlineExamListResponse {
    @SerializedName("online_exam_native")
    @Expose
    private var onlineExamNative: String? = null

    @SerializedName("examlist")
    @Expose
    private var examlist: List<ExamOnlineExamStaff?>? = null

    @SerializedName("permission_array")
    @Expose
    private var permissionArray: List<Any?>? = null

    fun getOnlineExamNative(): String? {
        return onlineExamNative
    }

    fun setOnlineExamNative(onlineExamNative: String?) {
        this.onlineExamNative = onlineExamNative
    }

    fun getExamlist(): List<ExamOnlineExamStaff?>? {
        if (examlist == null)
            return ArrayList()
        return examlist
    }

    fun setExamlist(examlist: List<ExamOnlineExamStaff?>?) {
        this.examlist = examlist
    }

    fun getPermissionArray(): List<Any?>? {
        return permissionArray
    }

    fun setPermissionArray(permissionArray: List<Any?>?) {
        this.permissionArray = permissionArray
    }
}