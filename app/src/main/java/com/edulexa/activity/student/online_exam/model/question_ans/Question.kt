package com.edulexa.activity.student.online_exam.model.question_ans

import android.net.Uri
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Question {

    var ansSubmit = false
    var subjectiveImageUpload = false
    var imageList : List<Uri?>? = null
    var documentFile : List<UploadFileModel?>? = null
    var submittedImageId : List<Int?>? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("question")
    @Expose
    private var question: String? = null

    @SerializedName("opt_a")
    @Expose
    private var optA: String? = null

    @SerializedName("opt_b")
    @Expose
    private var optB: String? = null

    @SerializedName("opt_c")
    @Expose
    private var optC: String? = null

    @SerializedName("opt_d")
    @Expose
    private var optD: String? = null

    @SerializedName("opt_e")
    @Expose
    private var optE: String? = null

    @SerializedName("correct")
    @Expose
    private var correct: Any? = null

    @SerializedName("q_type")
    @Expose
    private var qType: String? = null

    @SerializedName("mark")
    @Expose
    private var mark: String? = null

    @SerializedName("nmark")
    @Expose
    private var nmark: String? = null

    @SerializedName("partial")
    @Expose
    private var partial: String? = null

    @SerializedName("unattemp")
    @Expose
    private var unattemp: String? = null

    @SerializedName("tag")
    @Expose
    private var tag: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("onlineexam_questions_id")
    @Expose
    private var onlineexamQuestionsId: String? = null

    @SerializedName("q_type_name")
    @Expose
    private var qTypeName: String? = null

    fun isAnsSubmit(): Boolean {
        return ansSubmit
    }

    @JvmName("setAnsSubmit1")
    fun setAnsSubmit(ansSubmit: Boolean) {
        this.ansSubmit = ansSubmit
    }

    fun isSubjectiveImageUpload(): Boolean {
        return subjectiveImageUpload
    }

    @JvmName("setSubjectiveImageUpload1")
    fun setSubjectiveImageUpload(subjectiveImageUpload: Boolean) {
        this.subjectiveImageUpload = subjectiveImageUpload
    }

    @JvmName("getImageList1")
    fun getImageList(): List<Uri?>? {
        return imageList
    }

    @JvmName("setImageList1")
    fun setImageList(imageList: List<Uri?>?) {
        this.imageList = imageList
    }

    @JvmName("getDocumentFile1")
    fun getDocumentFile(): List<UploadFileModel?>? {
        return documentFile
    }

    @JvmName("setDocumentFile1")
    fun setDocumentFile(documentFile: List<UploadFileModel?>?) {
        this.documentFile = documentFile
    }

    @JvmName("getSubmittedImageId1")
    fun getSubmittedImageId(): List<Int?>? {
        return submittedImageId
    }

    @JvmName("setSubmittedImageId1")
    fun setSubmittedImageId(submittedImageId: List<Int?>?) {
        this.submittedImageId = submittedImageId
    }

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

    fun getQuestion(): String? {
        if (question == null)
            return ""
        return question
    }

    fun setQuestion(question: String?) {
        this.question = question
    }

    fun getOptA(): String? {
        if (optA == null)
            return ""
        return optA
    }

    fun setOptA(optA: String?) {
        this.optA = optA
    }

    fun getOptB(): String? {
        if (optB == null)
            return ""
        return optB
    }

    fun setOptB(optB: String?) {
        this.optB = optB
    }

    fun getOptC(): String? {
        if (optC == null)
            return ""
        return optC
    }

    fun setOptC(optC: String?) {
        this.optC = optC
    }

    fun getOptD(): String? {
        if (optD == null)
            return ""
        return optD
    }

    fun setOptD(optD: String?) {
        this.optD = optD
    }

    fun getOptE(): String? {
        if (optE == null)
            return ""
        return optE
    }

    fun setOptE(optE: String?) {
        this.optE = optE
    }

    fun getCorrect(): Any? {
        return correct
    }

    fun setCorrect(correct: Any?) {
        this.correct = correct
    }

    fun getqType(): String? {
        if (qType == null)
            return ""
        return qType
    }

    fun setqType(qType: String?) {
        this.qType = qType
    }

    fun getMark(): String? {
        if (mark == null)
            return "0"
        return mark
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

    fun getTag(): String? {
        return tag
    }

    fun setTag(tag: String?) {
        this.tag = tag
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

    fun getOnlineexamQuestionsId(): String? {
        return onlineexamQuestionsId
    }

    fun setOnlineexamQuestionsId(onlineexamQuestionsId: String?) {
        this.onlineexamQuestionsId = onlineexamQuestionsId
    }

    fun getqTypeName(): String? {
        if (qTypeName == null)
            return ""
        return qTypeName
    }

    fun setqTypeName(qTypeName: String?) {
        this.qTypeName = qTypeName
    }
}