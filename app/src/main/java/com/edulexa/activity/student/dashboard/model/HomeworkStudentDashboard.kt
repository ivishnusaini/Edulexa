package com.edulexa.activity.student.dashboard.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeworkStudentDashboard {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null
    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getDescription(): String? {
        if (description == null)
            return ""
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }
    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
    fun getSubjectId(): String? {
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

}