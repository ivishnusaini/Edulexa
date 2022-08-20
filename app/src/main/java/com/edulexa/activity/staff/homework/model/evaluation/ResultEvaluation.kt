package com.edulexa.activity.staff.homework.model.evaluation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultEvaluation {
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

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("subject_group")
    @Expose
    private var subjectGroup: String? = null

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
        return homeworkDate
    }

    fun setHomeworkDate(homeworkDate: String?) {
        this.homeworkDate = homeworkDate
    }

    fun getSubmitDate(): String? {
        return submitDate
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
        return if (description == null) "null" else description
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
        return if (document == null) "" else document
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
        return createdBy
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
        return _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getSection(): String? {
        return section
    }

    fun setSection(section: String?) {
        this.section = section
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getSubjectGroup(): String? {
        return subjectGroup
    }

    fun setSubjectGroup(subjectGroup: String?) {
        this.subjectGroup = subjectGroup
    }
}