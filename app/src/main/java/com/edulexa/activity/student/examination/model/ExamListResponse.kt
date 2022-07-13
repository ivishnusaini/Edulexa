package com.edulexa.activity.student.examination.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ExamListResponse {
    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("examSchedule")
    @Expose
    private var examSchedule: List<ExamSchedule?>? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getClassId(): String? {
        return classId
    }

    fun setClassId(classId: String?) {
        this.classId = classId
    }

    fun getSectionId(): String? {
        return sectionId
    }

    fun setSectionId(sectionId: String?) {
        this.sectionId = sectionId
    }

    fun getExamSchedule(): List<ExamSchedule?>? {
        return examSchedule
    }

    fun setExamSchedule(examSchedule: List<ExamSchedule?>?) {
        this.examSchedule = examSchedule
    }
}