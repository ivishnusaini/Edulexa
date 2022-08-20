package com.edulexa.activity.staff.online_exam.model.add_question

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Subject {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("code")
    @Expose
    private var code: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("mark_fields")
    @Expose
    private var markFields: String? = null

    @SerializedName("elective_subject")
    @Expose
    private var electiveSubject: Any? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
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

    fun getMarkFields(): String? {
        return markFields
    }

    fun setMarkFields(markFields: String?) {
        this.markFields = markFields
    }

    fun getElectiveSubject(): Any? {
        return electiveSubject
    }

    fun setElectiveSubject(electiveSubject: Any?) {
        this.electiveSubject = electiveSubject
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
}