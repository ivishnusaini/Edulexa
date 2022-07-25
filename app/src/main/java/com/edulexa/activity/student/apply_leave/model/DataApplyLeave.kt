package com.edulexa.activity.student.apply_leave.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DataApplyLeave {
    @SerializedName("list")
    @Expose
    private var list: List<ListApplyLeave?>? = null

    @SerializedName("studentclasses")
    @Expose
    private var studentclasses: List<StudentclassApplyLeave?>? = null

    fun getList(): List<ListApplyLeave?>? {
        return list
    }

    fun setList(list: List<ListApplyLeave?>?) {
        this.list = list
    }

    fun getStudentclasses(): List<StudentclassApplyLeave?>? {
        return studentclasses
    }

    fun setStudentclasses(studentclasses: List<StudentclassApplyLeave?>?) {
        this.studentclasses = studentclasses
    }
}