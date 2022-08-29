package com.edulexa.activity.staff.lesson_plan.model.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TimetableLessonPlan {
    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    @SerializedName("syllabus_id")
    @Expose
    private var syllabusId: String? = null

    fun getSyllabusId(): String? {
        return if (syllabusId == null) "0" else syllabusId
    }

    fun setSyllabusId(syllabusId: String?) {
        this.syllabusId = syllabusId
    }

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("subject_name")
    @Expose
    private var subjectName: String? = null

    @SerializedName("code")
    @Expose
    private var code: String? = null

    @SerializedName("elective_subject")
    @Expose
    private var electiveSubject: Any? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("day")
    @Expose
    private var day: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("subject_group_id")
    @Expose
    private var subjectGroupId: String? = null

    @SerializedName("subject_group_subject_id")
    @Expose
    private var subjectGroupSubjectId: String? = null

    @SerializedName("staff_id")
    @Expose
    private var staffId: String? = null

    @SerializedName("time_from")
    @Expose
    private var timeFrom: String? = null

    @SerializedName("time_to")
    @Expose
    private var timeTo: String? = null

    @SerializedName("room_no")
    @Expose
    private var roomNo: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    fun getClass_(): String? {
        return if (_class == null) "null" else _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getSection(): String? {
        return if (section == null) "null" else section
    }

    fun setSection(section: String?) {
        this.section = section
    }

    fun getSubjectId(): String? {
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getSubjectName(): String? {
        return if (subjectName == null) "null" else subjectName
    }

    fun setSubjectName(subjectName: String?) {
        this.subjectName = subjectName
    }

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String?) {
        this.code = code
    }

    fun getElectiveSubject(): Any? {
        return electiveSubject
    }

    fun setElectiveSubject(electiveSubject: Any?) {
        this.electiveSubject = electiveSubject
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getDay(): String? {
        return day
    }

    fun setDay(day: String?) {
        this.day = day
    }

    fun getClassId(): String? {
        if (classId == null)
            return ""
        return classId
    }

    fun setClassId(classId: String?) {
        this.classId = classId
    }

    fun getSectionId(): String? {
        if (sectionId == null)
            return ""
        return sectionId
    }

    fun setSectionId(sectionId: String?) {
        this.sectionId = sectionId
    }

    fun getSubjectGroupId(): String? {
        if (subjectGroupId == null)
            return ""
        return subjectGroupId
    }

    fun setSubjectGroupId(subjectGroupId: String?) {
        this.subjectGroupId = subjectGroupId
    }

    fun getSubjectGroupSubjectId(): String? {
        if (subjectGroupSubjectId == null)
            return ""
        return subjectGroupSubjectId
    }

    fun setSubjectGroupSubjectId(subjectGroupSubjectId: String?) {
        this.subjectGroupSubjectId = subjectGroupSubjectId
    }

    fun getStaffId(): String? {
        if (staffId == null)
            return ""
        return staffId
    }

    fun setStaffId(staffId: String?) {
        this.staffId = staffId
    }

    fun getTimeFrom(): String? {
        return if (timeFrom == null) "null" else timeFrom
    }

    fun setTimeFrom(timeFrom: String?) {
        this.timeFrom = timeFrom
    }

    fun getTimeTo(): String? {
        return if (timeTo == null) "null" else timeTo
    }

    fun setTimeTo(timeTo: String?) {
        this.timeTo = timeTo
    }

    fun getRoomNo(): String? {
        return roomNo
    }

    fun setRoomNo(roomNo: String?) {
        this.roomNo = roomNo
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }
}