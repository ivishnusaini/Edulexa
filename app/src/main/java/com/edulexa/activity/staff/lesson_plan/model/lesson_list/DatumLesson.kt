package com.edulexa.activity.staff.lesson_plan.model.lesson_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DatumLesson {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("subject_group_subject_id")
    @Expose
    private var subjectGroupSubjectId: String? = null

    @SerializedName("subject_group_class_sections_id")
    @Expose
    private var subjectGroupClassSectionsId: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getSubjectGroupSubjectId(): String? {
        return subjectGroupSubjectId
    }

    fun setSubjectGroupSubjectId(subjectGroupSubjectId: String?) {
        this.subjectGroupSubjectId = subjectGroupSubjectId
    }

    fun getSubjectGroupClassSectionsId(): String? {
        return subjectGroupClassSectionsId
    }

    fun setSubjectGroupClassSectionsId(subjectGroupClassSectionsId: String?) {
        this.subjectGroupClassSectionsId = subjectGroupClassSectionsId
    }

    fun getName(): String? {
        return if (name == null) "null" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }
}