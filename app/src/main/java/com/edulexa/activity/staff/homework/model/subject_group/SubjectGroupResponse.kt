package com.edulexa.activity.staff.homework.model.subject_group

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectGroupResponse {
    @SerializedName("subject_group_list")
    @Expose
    private var subjectGroupList: List<SubjectGroup?>? = null

    fun getSubjectGroupList(): List<SubjectGroup?>? {
        if (subjectGroupList == null)
            return ArrayList()
        return subjectGroupList
    }

    fun setSubjectGroupList(subjectGroupList: List<SubjectGroup?>?) {
        this.subjectGroupList = subjectGroupList
    }
}