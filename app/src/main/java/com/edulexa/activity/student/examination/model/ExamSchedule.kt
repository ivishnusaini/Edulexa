package com.edulexa.activity.student.examination.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ExamSchedule {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("exam_group_class_batch_exam_id")
    @Expose
    private var examGroupClassBatchExamId: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("roll_no")
    @Expose
    private var rollNo: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("exam")
    @Expose
    private var exam: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getExamGroupClassBatchExamId(): String? {
        if (examGroupClassBatchExamId == null)
            return ""
        return examGroupClassBatchExamId
    }

    fun setExamGroupClassBatchExamId(examGroupClassBatchExamId: String?) {
        this.examGroupClassBatchExamId = examGroupClassBatchExamId
    }

    fun getStudentId(): String? {
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }

    fun getStudentSessionId(): String? {
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }

    fun getRollNo(): String? {
        return rollNo
    }

    fun setRollNo(rollNo: String?) {
        this.rollNo = rollNo
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }

    fun getExam(): String? {
        if (exam == null)
            return ""
        return exam
    }

    fun setExam(exam: String?) {
        this.exam = exam
    }
}