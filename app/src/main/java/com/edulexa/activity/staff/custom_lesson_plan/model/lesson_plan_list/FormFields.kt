package com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FormFields {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("is_lesson")
    @Expose
    private var isLesson: String? = null

    @SerializedName("is_topic")
    @Expose
    private var isTopic: String? = null

    @SerializedName("is_general_objectives")
    @Expose
    private var isGeneralObjectives: String? = null

    @SerializedName("is_teaching_method")
    @Expose
    private var isTeachingMethod: String? = null

    @SerializedName("is_previous_knowledge")
    @Expose
    private var isPreviousKnowledge: String? = null

    @SerializedName("is_comprehensive_questions")
    @Expose
    private var isComprehensiveQuestions: String? = null

    @SerializedName("is_lecture_youtube_url")
    @Expose
    private var isLectureYoutubeUrl: String? = null

    @SerializedName("period")
    @Expose
    private var period: String? = null

    @SerializedName("teaching_aids")
    @Expose
    private var teachingAids: String? = null

    @SerializedName("portion_actually_taught")
    @Expose
    private var portionActuallyTaught: String? = null

    @SerializedName("hw_assigned")
    @Expose
    private var hwAssigned: String? = null

    @SerializedName("hw_not_assigned_reason")
    @Expose
    private var hwNotAssignedReason: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getIsLesson(): String? {
        return if (isLesson == null) "" else isLesson
    }

    fun setIsLesson(isLesson: String?) {
        this.isLesson = isLesson
    }

    fun getIsTopic(): String? {
        return if (isTopic == null) "" else isTopic
    }

    fun setIsTopic(isTopic: String?) {
        this.isTopic = isTopic
    }

    fun getIsGeneralObjectives(): String? {
        return if (isGeneralObjectives == null) "" else isGeneralObjectives
    }

    fun setIsGeneralObjectives(isGeneralObjectives: String?) {
        this.isGeneralObjectives = isGeneralObjectives
    }

    fun getIsTeachingMethod(): String? {
        return if (isTeachingMethod == null) "" else isTeachingMethod
    }

    fun setIsTeachingMethod(isTeachingMethod: String?) {
        this.isTeachingMethod = isTeachingMethod
    }

    fun getIsPreviousKnowledge(): String? {
        return if (isPreviousKnowledge == null) "" else isPreviousKnowledge
    }

    fun setIsPreviousKnowledge(isPreviousKnowledge: String?) {
        this.isPreviousKnowledge = isPreviousKnowledge
    }

    fun getIsComprehensiveQuestions(): String? {
        return if (isComprehensiveQuestions == null) "" else isComprehensiveQuestions
    }

    fun setIsComprehensiveQuestions(isComprehensiveQuestions: String?) {
        this.isComprehensiveQuestions = isComprehensiveQuestions
    }

    fun getIsLectureYoutubeUrl(): String? {
        return if (isLectureYoutubeUrl == null) "" else isLectureYoutubeUrl
    }

    fun setIsLectureYoutubeUrl(isLectureYoutubeUrl: String?) {
        this.isLectureYoutubeUrl = isLectureYoutubeUrl
    }

    fun getPeriod(): String? {
        return if (period == null) "" else period
    }

    fun setPeriod(period: String?) {
        this.period = period
    }

    fun getTeachingAids(): String? {
        return if (teachingAids == null) "" else teachingAids
    }

    fun setTeachingAids(teachingAids: String?) {
        this.teachingAids = teachingAids
    }

    fun getPortionActuallyTaught(): String? {
        return if (portionActuallyTaught == null) "" else portionActuallyTaught
    }

    fun setPortionActuallyTaught(portionActuallyTaught: String?) {
        this.portionActuallyTaught = portionActuallyTaught
    }

    fun getHwAssigned(): String? {
        return if (hwAssigned == null) "" else hwAssigned
    }

    fun setHwAssigned(hwAssigned: String?) {
        this.hwAssigned = hwAssigned
    }

    fun getHwNotAssignedReason(): String? {
        return if (hwNotAssignedReason == null) "" else hwNotAssignedReason
    }

    fun setHwNotAssignedReason(hwNotAssignedReason: String?) {
        this.hwNotAssignedReason = hwNotAssignedReason
    }
}