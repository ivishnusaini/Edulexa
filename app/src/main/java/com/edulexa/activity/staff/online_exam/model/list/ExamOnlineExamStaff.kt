package com.edulexa.activity.staff.online_exam.model.list

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ExamOnlineExamStaff {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("exam")
    @Expose
    private var exam: String? = null

    @SerializedName("attempt")
    @Expose
    private var attempt: String? = null

    @SerializedName("exam_from")
    @Expose
    private var examFrom: String? = null

    @SerializedName("exam_to")
    @Expose
    private var examTo: String? = null

    @SerializedName("time_from")
    @Expose
    private var timeFrom: Any? = null

    @SerializedName("time_to")
    @Expose
    private var timeTo: Any? = null

    @SerializedName("duration")
    @Expose
    private var duration: String? = null

    @SerializedName("passing_percentage")
    @Expose
    private var passingPercentage: String? = null

    @SerializedName("passing_mark_type")
    @Expose
    private var passingMarkType: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("publish_result")
    @Expose
    private var publishResult: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("shuffle_questions")
    @Expose
    private var shuffleQuestions: String? = null

    @SerializedName("rank_result")
    @Expose
    private var rankResult: String? = null

    @SerializedName("is_practice")
    @Expose
    private var isPractice: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("subjective_question")
    @Expose
    private var subjectiveQuestion: Int? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getExam(): String? {
        if (exam == null)
            return ""
        return exam
    }

    fun setExam(exam: String?) {
        this.exam = exam
    }

    fun getAttempt(): String? {
        return attempt
    }

    fun setAttempt(attempt: String?) {
        this.attempt = attempt
    }

    fun getExamFrom(): String? {
        if (examFrom == null)
            return ""
        return examFrom
    }

    fun setExamFrom(examFrom: String?) {
        this.examFrom = examFrom
    }

    fun getExamTo(): String? {
        if (examTo == null)
            return ""
        return examTo
    }

    fun setExamTo(examTo: String?) {
        this.examTo = examTo
    }

    fun getTimeFrom(): Any? {
        return timeFrom
    }

    fun setTimeFrom(timeFrom: Any?) {
        this.timeFrom = timeFrom
    }

    fun getTimeTo(): Any? {
        return timeTo
    }

    fun setTimeTo(timeTo: Any?) {
        this.timeTo = timeTo
    }

    fun getDuration(): String? {
        if (duration == null)
            return ""
        return duration
    }

    fun setDuration(duration: String?) {
        this.duration = duration
    }

    fun getPassingPercentage(): String? {
        if (passingPercentage == null)
            return ""
        return passingPercentage
    }

    fun setPassingPercentage(passingPercentage: String?) {
        this.passingPercentage = passingPercentage
    }

    fun getPassingMarkType(): String? {
        return passingMarkType
    }

    fun setPassingMarkType(passingMarkType: String?) {
        this.passingMarkType = passingMarkType
    }

    fun getDescription(): String? {
        if (description == null)
            return ""
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getPublishResult(): String? {
        return publishResult
    }

    fun setPublishResult(publishResult: String?) {
        this.publishResult = publishResult
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getShuffleQuestions(): String? {
        return shuffleQuestions
    }

    fun setShuffleQuestions(shuffleQuestions: String?) {
        this.shuffleQuestions = shuffleQuestions
    }

    fun getRankResult(): String? {
        return rankResult
    }

    fun setRankResult(rankResult: String?) {
        this.rankResult = rankResult
    }

    fun getIsPractice(): String? {
        if (isPractice == null)
            return ""
        return isPractice
    }

    fun setIsPractice(isPractice: String?) {
        this.isPractice = isPractice
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

    fun getCreatedBy(): String? {
        if (createdBy == null)
            return ""
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getSubjectiveQuestion(): Int? {
        return subjectiveQuestion
    }

    fun setSubjectiveQuestion(subjectiveQuestion: Int?) {
        this.subjectiveQuestion = subjectiveQuestion
    }
}