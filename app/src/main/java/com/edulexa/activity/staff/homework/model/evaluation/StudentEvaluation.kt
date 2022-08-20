package com.edulexa.activity.staff.homework.model.evaluation

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StudentEvaluation {
    var nameSelect = false
    var resubmitSelect = false

    fun isNameSelect(): Boolean {
        return nameSelect
    }

    @JvmName("setNameSelect1")
    fun setNameSelect(nameSelect: Boolean) {
        this.nameSelect = nameSelect
    }

    fun isResubmitSelect(): Boolean {
        return resubmitSelect
    }

    @JvmName("setResubmitSelect1")
    fun setResubmitSelect(resubmitSelect: Boolean) {
        this.resubmitSelect = resubmitSelect
    }

    @SerializedName("homework_evaluation_id")
    @Expose
    private var homeworkEvaluationId: String? = null

    @SerializedName("homework_id")
    @Expose
    private var homeworkId: Any? = null

    @SerializedName("comment")
    @Expose
    private var comment: String? = null

    @SerializedName("resubmit")
    @Expose
    private var resubmit: Any? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null

    @SerializedName("firstname")
    @Expose
    private var firstname: String? = null

    @SerializedName("lastname")
    @Expose
    private var lastname: String? = null

    @SerializedName("admission_no")
    @Expose
    private var admissionNo: String? = null

    @SerializedName("submit_doc")
    @Expose
    private var submitDoc: List<SubmitDoc?>? = null

    fun getHomeworkEvaluationId(): String? {
        return if (homeworkEvaluationId == null) "0" else homeworkEvaluationId
    }

    fun setHomeworkEvaluationId(homeworkEvaluationId: String?) {
        this.homeworkEvaluationId = homeworkEvaluationId
    }

    fun getHomeworkId(): Any? {
        return homeworkId
    }

    fun setHomeworkId(homeworkId: Any?) {
        this.homeworkId = homeworkId
    }

    fun getComment(): String? {
        return if (comment == null) "" else comment
    }

    fun setComment(comment: String?) {
        this.comment = comment
    }

    fun getResubmit(): Any? {
        return resubmit
    }

    fun setResubmit(resubmit: Any?) {
        this.resubmit = resubmit
    }

    fun getStudentSessionId(): String? {
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
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

    fun getStudentId(): String? {
        return if (studentId == null) "" else studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }

    fun getFirstname(): String? {
        return if (firstname == null) "null" else firstname
    }

    fun setFirstname(firstname: String?) {
        this.firstname = firstname
    }

    fun getLastname(): String? {
        return lastname
    }

    fun setLastname(lastname: String?) {
        this.lastname = lastname
    }

    fun getAdmissionNo(): String? {
        return if (admissionNo == null) "null" else admissionNo
    }

    fun setAdmissionNo(admissionNo: String?) {
        this.admissionNo = admissionNo
    }

    fun getSubmitDoc(): List<SubmitDoc?>? {
        return submitDoc
    }

    fun setSubmitDoc(submitDoc: List<SubmitDoc?>?) {
        this.submitDoc = submitDoc
    }
}