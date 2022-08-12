package com.edulexa.activity.staff.custom_lesson_plan.model.subject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectResponse {
    @SerializedName("subject_list")
    @Expose
    private var subjectList: List<Subject?>? = null

    fun getSubjectList(): List<Subject?>? {
        if (subjectList == null)
            return ArrayList()
        return subjectList
    }

    fun setSubjectList(subjectList: List<Subject?>?) {
        this.subjectList = subjectList
    }
}