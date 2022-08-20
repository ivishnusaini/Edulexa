package com.edulexa.activity.staff.homework.model.subject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Subject {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("subject_group_id")
    @Expose
    private var subjectGroupId: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("elective_category_id")
    @Expose
    private var electiveCategoryId: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("code")
    @Expose
    private var code: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("elective_subject")
    @Expose
    private var electiveSubject: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSubjectGroupId(): String? {
        return subjectGroupId
    }

    fun setSubjectGroupId(subjectGroupId: String?) {
        this.subjectGroupId = subjectGroupId
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getSubjectId(): String? {
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getElectiveCategoryId(): String? {
        return electiveCategoryId
    }

    fun setElectiveCategoryId(electiveCategoryId: String?) {
        this.electiveCategoryId = electiveCategoryId
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getName(): String? {
        return if (name == null) "null" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String?) {
        this.code = code
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getElectiveSubject(): String? {
        return electiveSubject
    }

    fun setElectiveSubject(electiveSubject: String?) {
        this.electiveSubject = electiveSubject
    }
}