package com.edulexa.activity.student.report_card.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumReportCardList {
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

    @SerializedName("exam_group_id")
    @Expose
    private var examGroupId: String? = null

    @SerializedName("exam")
    @Expose
    private var exam: String? = null

    @SerializedName("date_from")
    @Expose
    private var dateFrom: Any? = null

    @SerializedName("date_to")
    @Expose
    private var dateTo: Any? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("is_publish")
    @Expose
    private var isPublish: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("exam_type")
    @Expose
    private var examType: String? = null

    @SerializedName("total_marks")
    @Expose
    private var totalMarks: Int? = null

    @SerializedName("get_marks")
    @Expose
    private var getMarks: Int? = null

    @SerializedName("download_marksheet")
    @Expose
    private var downloadMarksheet: String? = null

    @SerializedName("percentage")
    @Expose
    private var percentage: String? = null

    @SerializedName("grade")
    @Expose
    private var grade: String? = null

    @SerializedName("result")
    @Expose
    private var result: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
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

    fun getExamGroupId(): String? {
        return examGroupId
    }

    fun setExamGroupId(examGroupId: String?) {
        this.examGroupId = examGroupId
    }

    fun getExam(): String? {
        if (exam == null)
            return ""
        return exam
    }

    fun setExam(exam: String?) {
        this.exam = exam
    }

    fun getDateFrom(): Any? {
        return dateFrom
    }

    fun setDateFrom(dateFrom: Any?) {
        this.dateFrom = dateFrom
    }

    fun getDateTo(): Any? {
        return dateTo
    }

    fun setDateTo(dateTo: Any?) {
        this.dateTo = dateTo
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getIsPublish(): String? {
        return isPublish
    }

    fun setIsPublish(isPublish: String?) {
        this.isPublish = isPublish
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getExamType(): String? {
        return examType
    }

    fun setExamType(examType: String?) {
        this.examType = examType
    }

    fun getTotalMarks(): Int? {
        return totalMarks
    }

    fun setTotalMarks(totalMarks: Int?) {
        this.totalMarks = totalMarks
    }

    fun getGetMarks(): Int? {
        return getMarks
    }

    fun setGetMarks(getMarks: Int?) {
        this.getMarks = getMarks
    }

    fun getDownloadMarksheet(): String? {
        if (downloadMarksheet == null)
            return ""
        return downloadMarksheet
    }

    fun setDownloadMarksheet(downloadMarksheet: String?) {
        this.downloadMarksheet = downloadMarksheet
    }

    fun getPercentage(): String? {
        return percentage
    }

    fun setPercentage(percentage: String?) {
        this.percentage = percentage
    }

    fun getGrade(): String? {
        return grade
    }

    fun setGrade(grade: String?) {
        this.grade = grade
    }

    fun getResult(): String? {
        if (result == null)
            return ""
        return result
    }

    fun setResult(result: String?) {
        this.result = result
    }
}