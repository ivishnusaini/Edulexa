package com.edulexa.activity.staff.lesson_plan.model.syllabus_detail

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataSyllabusDetail {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("topic_id")
    @Expose
    private var topicId: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("created_for")
    @Expose
    private var createdFor: String? = null

    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("time_from")
    @Expose
    private var timeFrom: String? = null

    @SerializedName("time_to")
    @Expose
    private var timeTo: String? = null

    @SerializedName("presentation")
    @Expose
    private var presentation: String? = null

    @SerializedName("attachment")
    @Expose
    private var attachment: String? = null

    @SerializedName("lacture_youtube_url")
    @Expose
    private var lactureYoutubeUrl: String? = null

    @SerializedName("lacture_video")
    @Expose
    private var lactureVideo: String? = null

    @SerializedName("sub_topic")
    @Expose
    private var subTopic: String? = null

    @SerializedName("teaching_method")
    @Expose
    private var teachingMethod: String? = null

    @SerializedName("general_objectives")
    @Expose
    private var generalObjectives: String? = null

    @SerializedName("previous_knowledge")
    @Expose
    private var previousKnowledge: String? = null

    @SerializedName("comprehensive_questions")
    @Expose
    private var comprehensiveQuestions: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("subject_group_subject_id")
    @Expose
    private var subjectGroupSubjectId: String? = null

    @SerializedName("subject_group_class_sections_id")
    @Expose
    private var subjectGroupClassSectionsId: String? = null

    @SerializedName("lesson_id")
    @Expose
    private var lessonId: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getTopicId(): String? {
        return topicId
    }

    fun setTopicId(topicId: String?) {
        this.topicId = topicId
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getCreatedBy(): String? {
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getCreatedFor(): String? {
        return if (createdFor == null) "" else createdFor
    }

    fun setCreatedFor(createdFor: String?) {
        this.createdFor = createdFor
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getTimeFrom(): String? {
        return timeFrom
    }

    fun setTimeFrom(timeFrom: String?) {
        this.timeFrom = timeFrom
    }

    fun getTimeTo(): String? {
        return timeTo
    }

    fun setTimeTo(timeTo: String?) {
        this.timeTo = timeTo
    }

    fun getPresentation(): String? {
        return if (presentation == null) "null" else presentation
    }

    fun setPresentation(presentation: String?) {
        this.presentation = presentation
    }

    fun getAttachment(): String? {
        return if (attachment == null) "null" else attachment
    }

    fun setAttachment(attachment: String?) {
        this.attachment = attachment
    }

    fun getLactureYoutubeUrl(): String? {
        return if (lactureYoutubeUrl == null) "null" else lactureYoutubeUrl
    }

    fun setLactureYoutubeUrl(lactureYoutubeUrl: String?) {
        this.lactureYoutubeUrl = lactureYoutubeUrl
    }

    fun getLactureVideo(): String? {
        return lactureVideo
    }

    fun setLactureVideo(lactureVideo: String?) {
        this.lactureVideo = lactureVideo
    }

    fun getSubTopic(): String? {
        return if (subTopic == null) "null" else subTopic
    }

    fun setSubTopic(subTopic: String?) {
        this.subTopic = subTopic
    }

    fun getTeachingMethod(): String? {
        return if (teachingMethod == null) "null" else teachingMethod
    }

    fun setTeachingMethod(teachingMethod: String?) {
        this.teachingMethod = teachingMethod
    }

    fun getGeneralObjectives(): String? {
        return if (generalObjectives == null) "null" else generalObjectives
    }

    fun setGeneralObjectives(generalObjectives: String?) {
        this.generalObjectives = generalObjectives
    }

    fun getPreviousKnowledge(): String? {
        return if (previousKnowledge == null) "null" else previousKnowledge
    }

    fun setPreviousKnowledge(previousKnowledge: String?) {
        this.previousKnowledge = previousKnowledge
    }

    fun getComprehensiveQuestions(): String? {
        return if (comprehensiveQuestions == null) "null" else comprehensiveQuestions
    }

    fun setComprehensiveQuestions(comprehensiveQuestions: String?) {
        this.comprehensiveQuestions = comprehensiveQuestions
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getSubjectGroupSubjectId(): String? {
        return subjectGroupSubjectId
    }

    fun setSubjectGroupSubjectId(subjectGroupSubjectId: String?) {
        this.subjectGroupSubjectId = subjectGroupSubjectId
    }

    fun getSubjectGroupClassSectionsId(): String? {
        return subjectGroupClassSectionsId
    }

    fun setSubjectGroupClassSectionsId(subjectGroupClassSectionsId: String?) {
        this.subjectGroupClassSectionsId = subjectGroupClassSectionsId
    }

    fun getLessonId(): String? {
        return lessonId
    }

    fun setLessonId(lessonId: String?) {
        this.lessonId = lessonId
    }
}