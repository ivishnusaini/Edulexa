package com.edulexa.activity.staff.lesson_plan.model.topic_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DatumTopic {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("lesson_id")
    @Expose
    private var lessonId: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("complete_date")
    @Expose
    private var completeDate: String? = null

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

    fun getLessonId(): String? {
        return lessonId
    }

    fun setLessonId(lessonId: String?) {
        this.lessonId = lessonId
    }

    fun getName(): String? {
        return if (name == null) "null" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getCompleteDate(): String? {
        return completeDate
    }

    fun setCompleteDate(completeDate: String?) {
        this.completeDate = completeDate
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }
}