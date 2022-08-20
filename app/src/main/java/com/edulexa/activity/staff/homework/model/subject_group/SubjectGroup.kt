package com.edulexa.activity.staff.homework.model.subject_group

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectGroup {
    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("subject_group_id")
    @Expose
    private var subjectGroupId: String? = null

    @SerializedName("class_section_id")
    @Expose
    private var classSectionId: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("description")
    @Expose
    private var description: Any? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getName(): String? {
        return if (name == null) "null" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSubjectGroupId(): String? {
        if (subjectGroupId == null)
            return ""
        return subjectGroupId
    }

    fun setSubjectGroupId(subjectGroupId: String?) {
        this.subjectGroupId = subjectGroupId
    }

    fun getClassSectionId(): String? {
        return classSectionId
    }

    fun setClassSectionId(classSectionId: String?) {
        this.classSectionId = classSectionId
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getDescription(): Any? {
        return description
    }

    fun setDescription(description: Any?) {
        this.description = description
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