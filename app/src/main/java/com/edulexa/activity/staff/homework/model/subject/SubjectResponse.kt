package com.edulexa.activity.staff.homework.model.subject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectResponse {
    @SerializedName("subject_list")
    @Expose
    private var subjectList: List<Subject?>? = null

    fun getSubjectList(): List<Subject?>? {
        return subjectList
    }

    fun setSubjectList(subjectList: List<Subject?>?) {
        this.subjectList = subjectList
    }
}