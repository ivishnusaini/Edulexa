package com.edulexa.activity.staff.online_exam.model.examwise_questions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QuestionExamWise {
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
    private var correct: String? = null

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

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("img_url")
    @Expose
    private var imgUrl: String? = null

    fun getImgUrl(): String? {
        return if (imgUrl == null) "" else imgUrl
    }

    fun setImgUrl(imgUrl: String?) {
        this.imgUrl = imgUrl
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSubjectId(): String? {
        return if (subjectId == null) "" else subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getQuestion(): String? {
        return if (question == null) "" else question
    }

    fun setQuestion(question: String?) {
        this.question = question
    }

    fun getOptA(): String? {
        return if (optA == null) "" else optA
    }

    fun setOptA(optA: String?) {
        this.optA = optA
    }

    fun getOptB(): String? {
        return if (optB == null) "" else optB
    }

    fun setOptB(optB: String?) {
        this.optB = optB
    }

    fun getOptC(): String? {
        return if (optC == null) "" else optC
    }

    fun setOptC(optC: String?) {
        this.optC = optC
    }

    fun getOptD(): String? {
        return if (optD == null) "" else optD
    }

    fun setOptD(optD: String?) {
        this.optD = optD
    }

    fun getOptE(): String? {
        return optE
    }

    fun setOptE(optE: String?) {
        this.optE = optE
    }

    fun getCorrect(): String? {
        return if (correct == null) "" else correct
    }

    fun setCorrect(correct: String?) {
        this.correct = correct
    }

    fun getqType(): String? {
        return if (qType == null) "" else qType
    }

    fun setqType(qType: String?) {
        this.qType = qType
    }

    fun getMark(): String? {
        return if (mark == null) "" else mark
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

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}