package com.edulexa.activity.staff.k12_diary.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Timeline {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("timeline_date")
    @Expose
    private var timelineDate: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("document")
    @Expose
    private var document: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("date")
    @Expose
    private var date: String? = null

    fun getId(): String? {
        return if (id == null) "" else id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getStudentId(): String? {
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }

    fun getTitle(): String? {
        return if (title == null) "" else title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getTimelineDate(): String? {
        return timelineDate
    }

    fun setTimelineDate(timelineDate: String?) {
        this.timelineDate = timelineDate
    }

    fun getDescription(): String? {
        return if (description == null) "" else description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getDocument(): String? {
        return if (document == null) "" else document
    }

    fun setDocument(document: String?) {
        this.document = document
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getDate(): String? {
        return if (date == null) "" else date
    }

    fun setDate(date: String?) {
        this.date = date
    }
}