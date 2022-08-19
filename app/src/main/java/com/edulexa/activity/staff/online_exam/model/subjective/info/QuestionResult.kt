package com.edulexa.activity.staff.online_exam.model.subjective.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QuestionResult {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("question_id")
    @Expose
    private var questionId: String? = null

    @SerializedName("onlineexam_id")
    @Expose
    private var onlineexamId: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: Any? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("subject_name")
    @Expose
    private var subjectName: String? = null

    @SerializedName("onlineexam_student_result_id")
    @Expose
    private var onlineexamStudentResultId: String? = null

    @SerializedName("question")
    @Expose
    private var question: String? = null

    @SerializedName("opt_a")
    @Expose
    private var optA: Any? = null

    @SerializedName("opt_b")
    @Expose
    private var optB: Any? = null

    @SerializedName("opt_c")
    @Expose
    private var optC: Any? = null

    @SerializedName("opt_d")
    @Expose
    private var optD: Any? = null

    @SerializedName("opt_e")
    @Expose
    private var optE: Any? = null

    @SerializedName("correct")
    @Expose
    private var correct: Any? = null

    @SerializedName("qid")
    @Expose
    private var qid: String? = null

    @SerializedName("q_type")
    @Expose
    private var qType: String? = null

    @SerializedName("partial")
    @Expose
    private var partial: String? = null

    @SerializedName("unattemp")
    @Expose
    private var unattemp: String? = null

    @SerializedName("mark")
    @Expose
    private var mark: String? = null

    @SerializedName("nmark")
    @Expose
    private var nmark: String? = null

    @SerializedName("select_option")
    @Expose
    private var selectOption: String? = null

    @SerializedName("subjective_mark")
    @Expose
    private var subjectiveMark: String? = null

    @SerializedName("images_array")
    @Expose
    private var imagesArray: List<ImagesArray?>? = null

    fun getImagesArray(): List<ImagesArray?>? {
        if (imagesArray == null)
            return ArrayList()
        return imagesArray
    }

    fun setImagesArray(imagesArray: List<ImagesArray?>?) {
        this.imagesArray = imagesArray
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getQuestionId(): String? {
        return questionId
    }

    fun setQuestionId(questionId: String?) {
        this.questionId = questionId
    }

    fun getOnlineexamId(): String? {
        return onlineexamId
    }

    fun setOnlineexamId(onlineexamId: String?) {
        this.onlineexamId = onlineexamId
    }

    fun getSessionId(): Any? {
        return sessionId
    }

    fun setSessionId(sessionId: Any?) {
        this.sessionId = sessionId
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }

    fun getSubjectName(): String? {
        return subjectName
    }

    fun setSubjectName(subjectName: String?) {
        this.subjectName = subjectName
    }

    fun getOnlineexamStudentResultId(): String? {
        return if (onlineexamStudentResultId == null) "" else onlineexamStudentResultId
    }

    fun setOnlineexamStudentResultId(onlineexamStudentResultId: String?) {
        this.onlineexamStudentResultId = onlineexamStudentResultId
    }

    fun getQuestion(): String? {
        return if (question == null) "null" else question
    }

    fun setQuestion(question: String?) {
        this.question = question
    }

    fun getOptA(): Any? {
        return optA
    }

    fun setOptA(optA: Any?) {
        this.optA = optA
    }

    fun getOptB(): Any? {
        return optB
    }

    fun setOptB(optB: Any?) {
        this.optB = optB
    }

    fun getOptC(): Any? {
        return optC
    }

    fun setOptC(optC: Any?) {
        this.optC = optC
    }

    fun getOptD(): Any? {
        return optD
    }

    fun setOptD(optD: Any?) {
        this.optD = optD
    }

    fun getOptE(): Any? {
        return optE
    }

    fun setOptE(optE: Any?) {
        this.optE = optE
    }

    fun getCorrect(): Any? {
        return correct
    }

    fun setCorrect(correct: Any?) {
        this.correct = correct
    }

    fun getQid(): String? {
        return qid
    }

    fun setQid(qid: String?) {
        this.qid = qid
    }

    fun getqType(): String? {
        return qType
    }

    fun setqType(qType: String?) {
        this.qType = qType
    }

    fun getPartial(): String? {
        return partial
    }

    fun setPartial(partial: String?) {
        this.partial = partial
    }

    fun getUnattemp(): String? {
        return unattemp
    }

    fun setUnattemp(unattemp: String?) {
        this.unattemp = unattemp
    }

    fun getMark(): String? {
        return if (mark == null) "0" else mark
    }

    fun setMark(mark: String?) {
        this.mark = mark
    }

    fun getNmark(): String? {
        return nmark
    }

    fun setNmark(nmark: String?) {
        this.nmark = nmark
    }

    fun getSelectOption(): String? {
        return if (selectOption == null) "null" else selectOption
    }

    fun setSelectOption(selectOption: String?) {
        this.selectOption = selectOption
    }

    fun getSubjectiveMark(): String? {
        return if (subjectiveMark == null) "" else subjectiveMark
    }

    fun setSubjectiveMark(subjectiveMark: String?) {
        this.subjectiveMark = subjectiveMark
    }
}