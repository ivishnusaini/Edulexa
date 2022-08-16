package com.edulexa.activity.staff.online_exam.model.add_question

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class QuestionTypeResponse {
    @SerializedName("type_list")
    @Expose
    private var typeList: List<QuestionType?>? = null

    @SerializedName("subject_list")
    @Expose
    private var subjectList: List<Subject?>? = null

    fun getTypeList(): List<QuestionType?>? {
        if (typeList == null)
            return ArrayList()
        return typeList
    }

    fun setTypeList(typeList: List<QuestionType?>?) {
        this.typeList = typeList
    }

    fun getSubjectList(): List<Subject?>? {
        if (subjectList == null)
            return ArrayList()
        return subjectList
    }

    fun setSubjectList(subjectList: List<Subject?>?) {
        this.subjectList = subjectList
    }
}