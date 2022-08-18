package com.edulexa.activity.staff.online_exam.model.subjective.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectiveListResponse {
    @SerializedName("exam_id")
    @Expose
    private var examId: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("results")
    @Expose
    private var results: List<ResultList?>? = null

    fun getExamId(): String? {
        return examId
    }

    fun setExamId(examId: String?) {
        this.examId = examId
    }

    fun getClassId(): String? {
        return classId
    }

    fun setClassId(classId: String?) {
        this.classId = classId
    }

    fun getSectionId(): String? {
        return sectionId
    }

    fun setSectionId(sectionId: String?) {
        this.sectionId = sectionId
    }

    fun getResults(): List<ResultList?>? {
        if (results == null)
            return ArrayList()
        return results
    }

    fun setResults(results: List<ResultList?>?) {
        this.results = results
    }
}