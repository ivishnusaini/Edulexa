package com.edulexa.activity.student.examination.model.exam_detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class DatumExamDetail {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("exam_group_class_batch_exams_id")
    @Expose
    private var examGroupClassBatchExamsId: String? = null

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("date_from")
    @Expose
    private var dateFrom: String? = null

    @SerializedName("time_from")
    @Expose
    private var timeFrom: String? = null

    @SerializedName("duration")
    @Expose
    private var duration: String? = null

    @SerializedName("room_no")
    @Expose
    private var roomNo: String? = null

    @SerializedName("max_marks")
    @Expose
    private var maxMarks: String? = null

    @SerializedName("min_marks")
    @Expose
    private var minMarks: String? = null

    @SerializedName("fields_mark")
    @Expose
    private var fieldsMark: String? = null

    @SerializedName("fields_id")
    @Expose
    private var fieldsId: String? = null

    @SerializedName("credit_hours")
    @Expose
    private var creditHours: String? = null

    @SerializedName("date_to")
    @Expose
    private var dateTo: Any? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("class_batch_subject_id")
    @Expose
    private var classBatchSubjectId: Any? = null

    @SerializedName("subject_name")
    @Expose
    private var subjectName: String? = null

    @SerializedName("subject_code")
    @Expose
    private var subjectCode: String? = null

    @SerializedName("subject_type")
    @Expose
    private var subjectType: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getExamGroupClassBatchExamsId(): String? {
        return examGroupClassBatchExamsId
    }

    fun setExamGroupClassBatchExamsId(examGroupClassBatchExamsId: String?) {
        this.examGroupClassBatchExamsId = examGroupClassBatchExamsId
    }

    fun getSubjectId(): String? {
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getDateFrom(): String? {
        if (dateFrom == null)
            return ""
        return dateFrom
    }

    fun setDateFrom(dateFrom: String?) {
        this.dateFrom = dateFrom
    }

    fun getTimeFrom(): String? {
        if (timeFrom == null)
            return ""
        return timeFrom
    }

    fun setTimeFrom(timeFrom: String?) {
        this.timeFrom = timeFrom
    }

    fun getDuration(): String? {
        if (duration == null)
            return "0"
        return duration
    }

    fun setDuration(duration: String?) {
        this.duration = duration
    }

    fun getRoomNo(): String? {
        if (roomNo == null)
            return ""
        return roomNo
    }

    fun setRoomNo(roomNo: String?) {
        this.roomNo = roomNo
    }

    fun getMaxMarks(): String? {
        return maxMarks
    }

    fun setMaxMarks(maxMarks: String?) {
        this.maxMarks = maxMarks
    }

    fun getMinMarks(): String? {
        return minMarks
    }

    fun setMinMarks(minMarks: String?) {
        this.minMarks = minMarks
    }

    fun getFieldsMark(): String? {
        return fieldsMark
    }

    fun setFieldsMark(fieldsMark: String?) {
        this.fieldsMark = fieldsMark
    }

    fun getFieldsId(): String? {
        return fieldsId
    }

    fun setFieldsId(fieldsId: String?) {
        this.fieldsId = fieldsId
    }

    fun getCreditHours(): String? {
        return creditHours
    }

    fun setCreditHours(creditHours: String?) {
        this.creditHours = creditHours
    }

    fun getDateTo(): Any? {
        return dateTo
    }

    fun setDateTo(dateTo: Any?) {
        this.dateTo = dateTo
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

    fun getClassBatchSubjectId(): Any? {
        return classBatchSubjectId
    }

    fun setClassBatchSubjectId(classBatchSubjectId: Any?) {
        this.classBatchSubjectId = classBatchSubjectId
    }

    fun getSubjectName(): String? {
        if (subjectName == null)
            return ""
        return subjectName
    }

    fun setSubjectName(subjectName: String?) {
        this.subjectName = subjectName
    }

    fun getSubjectCode(): String? {
        return subjectCode
    }

    fun setSubjectCode(subjectCode: String?) {
        this.subjectCode = subjectCode
    }

    fun getSubjectType(): String? {
        return subjectType
    }

    fun setSubjectType(subjectType: String?) {
        this.subjectType = subjectType
    }
}