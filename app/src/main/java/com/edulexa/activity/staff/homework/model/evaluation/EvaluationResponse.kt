package com.edulexa.activity.staff.homework.model.evaluation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EvaluationResponse {
    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("evaluated_by")
    @Expose
    private var evaluatedBy: String? = null

    @SerializedName("studentlist")
    @Expose
    private var studentlist: List<StudentEvaluation?>? = null

    @SerializedName("result")
    @Expose
    private var result: ResultEvaluation? = null

    @SerializedName("homework_id")
    @Expose
    private var homeworkId: String? = null

    fun getCreatedBy(): String? {
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getEvaluatedBy(): String? {
        return evaluatedBy
    }

    fun setEvaluatedBy(evaluatedBy: String?) {
        this.evaluatedBy = evaluatedBy
    }

    fun getStudentlist(): List<StudentEvaluation?>? {
        if (studentlist == null)
            return ArrayList()
        return studentlist
    }

    fun setStudentlist(studentlist: List<StudentEvaluation?>?) {
        this.studentlist = studentlist
    }

    fun getResult(): ResultEvaluation? {
        return result
    }

    fun setResult(result: ResultEvaluation?) {
        this.result = result
    }

    fun getHomeworkId(): String? {
        return homeworkId
    }

    fun setHomeworkId(homeworkId: String?) {
        this.homeworkId = homeworkId
    }
}