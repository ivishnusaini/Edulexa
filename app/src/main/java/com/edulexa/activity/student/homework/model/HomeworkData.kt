package com.edulexa.activity.student.homework.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class HomeworkData {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSubjectId(): String? {
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getDescription(): String? {
        if (description == null)
            return ""
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }
}