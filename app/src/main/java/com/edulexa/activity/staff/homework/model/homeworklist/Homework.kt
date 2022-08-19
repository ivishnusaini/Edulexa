package com.edulexa.activity.staff.homework.model.homeworklist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Homework {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("homework_date")
    @Expose
    private var homeworkDate: String? = null

    @SerializedName("submit_date")
    @Expose
    private var submitDate: String? = null

    @SerializedName("staff_id")
    @Expose
    private var staffId: String? = null

    @SerializedName("subject_group_subject_id")
    @Expose
    private var subjectGroupSubjectId: String? = null

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("create_date")
    @Expose
    private var createDate: String? = null

    @SerializedName("evaluation_date")
    @Expose
    private var evaluationDate: String? = null

    @SerializedName("document")
    @Expose
    private var document: String? = null

    @SerializedName("document_2")
    @Expose
    private var document2: String? = null

    @SerializedName("document_3")
    @Expose
    private var document3: String? = null

    @SerializedName("document_4")
    @Expose
    private var document4: String? = null

    @SerializedName("document_5")
    @Expose
    private var document5: String? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("evaluated_by")
    @Expose
    private var evaluatedBy: String? = null

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    @SerializedName("subject_name")
    @Expose
    private var subjectName: String? = null

    @SerializedName("elective_subject")
    @Expose
    private var electiveSubject: Any? = null

    @SerializedName("subject_groups_id")
    @Expose
    private var subjectGroupsId: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("assignments")
    @Expose
    private var assignments: String? = null

    @SerializedName("report")
    @Expose
    private var report: List<Any?>? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
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

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getHomeworkDate(): String? {
        return if (homeworkDate == null) "null" else homeworkDate
    }

    fun setHomeworkDate(homeworkDate: String?) {
        this.homeworkDate = homeworkDate
    }

    fun getSubmitDate(): String? {
        return if (submitDate == null) "null" else submitDate
    }

    fun setSubmitDate(submitDate: String?) {
        this.submitDate = submitDate
    }

    fun getStaffId(): String? {
        return staffId
    }

    fun setStaffId(staffId: String?) {
        this.staffId = staffId
    }

    fun getSubjectGroupSubjectId(): String? {
        return subjectGroupSubjectId
    }

    fun setSubjectGroupSubjectId(subjectGroupSubjectId: String?) {
        this.subjectGroupSubjectId = subjectGroupSubjectId
    }

    fun getSubjectId(): String? {
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getCreateDate(): String? {
        return createDate
    }

    fun setCreateDate(createDate: String?) {
        this.createDate = createDate
    }

    fun getEvaluationDate(): String? {
        return if (evaluationDate == null) "null" else evaluationDate
    }

    fun setEvaluationDate(evaluationDate: String?) {
        this.evaluationDate = evaluationDate
    }

    fun getDocument(): String? {
        return document
    }

    fun setDocument(document: String?) {
        this.document = document
    }

    fun getDocument2(): String? {
        return document2
    }

    fun setDocument2(document2: String?) {
        this.document2 = document2
    }

    fun getDocument3(): String? {
        return document3
    }

    fun setDocument3(document3: String?) {
        this.document3 = document3
    }

    fun getDocument4(): String? {
        return document4
    }

    fun setDocument4(document4: String?) {
        this.document4 = document4
    }

    fun getDocument5(): String? {
        return document5
    }

    fun setDocument5(document5: String?) {
        this.document5 = document5
    }

    fun getCreatedBy(): String? {
        return if (createdBy == null) "null" else createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getEvaluatedBy(): String? {
        return evaluatedBy
    }

    fun setEvaluatedBy(evaluatedBy: String?) {
        this.evaluatedBy = evaluatedBy
    }

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

    fun getSubjectName(): String? {
        return if (subjectName == null) "null" else subjectName
    }

    fun setSubjectName(subjectName: String?) {
        this.subjectName = subjectName
    }

    fun getElectiveSubject(): Any? {
        return electiveSubject
    }

    fun setElectiveSubject(electiveSubject: Any?) {
        this.electiveSubject = electiveSubject
    }

    fun getSubjectGroupsId(): String? {
        return subjectGroupsId
    }

    fun setSubjectGroupsId(subjectGroupsId: String?) {
        this.subjectGroupsId = subjectGroupsId
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getAssignments(): String? {
        return assignments
    }

    fun setAssignments(assignments: String?) {
        this.assignments = assignments
    }

    fun getReport(): List<Any?>? {
        return report
    }

    fun setReport(report: List<Any?>?) {
        this.report = report
    }
}