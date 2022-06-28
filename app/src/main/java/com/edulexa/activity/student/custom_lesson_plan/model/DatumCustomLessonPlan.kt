package com.edulexa.activity.student.custom_lesson_plan.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumCustomLessonPlan {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("subject_id")
    @Expose
    private var subjectId: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("cls_sec_id")
    @Expose
    private var clsSecId: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("note")
    @Expose
    private var note: String? = null

    @SerializedName("file")
    @Expose
    private var file: String? = null

    @SerializedName("lesson")
    @Expose
    private var lesson: String? = null

    @SerializedName("topic")
    @Expose
    private var topic: String? = null

    @SerializedName("general_objectives")
    @Expose
    private var generalObjectives: String? = null

    @SerializedName("teaching_method")
    @Expose
    private var teachingMethod: String? = null

    @SerializedName("previous_knowledge")
    @Expose
    private var previousKnowledge: String? = null

    @SerializedName("comprehensive_questions")
    @Expose
    private var comprehensiveQuestions: String? = null

    @SerializedName("lecture_youtube_url")
    @Expose
    private var lectureYoutubeUrl: String? = null

    @SerializedName("show_on_date")
    @Expose
    private var showOnDate: String? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

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

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getTitle(): String? {
        if (title == null)
            return ""
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getSubjectId(): String? {
        return subjectId
    }

    fun setSubjectId(subjectId: String?) {
        this.subjectId = subjectId
    }

    fun getClassId(): String? {
        return classId
    }

    fun setClassId(classId: String?) {
        this.classId = classId
    }

    fun getClsSecId(): String? {
        return clsSecId
    }

    fun setClsSecId(clsSecId: String?) {
        this.clsSecId = clsSecId
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getNote(): String? {
        return note
    }

    fun setNote(note: String?) {
        this.note = note
    }

    fun getFile(): String? {
        if (file == null)
            return ""
        return file
    }

    fun setFile(file: String?) {
        this.file = file
    }

    fun getLesson(): String? {
        if (lesson == null)
            return ""
        return lesson
    }

    fun setLesson(lesson: String?) {
        this.lesson = lesson
    }

    fun getTopic(): String? {
        if (topic == null)
            return ""
        return topic
    }

    fun setTopic(topic: String?) {
        this.topic = topic
    }

    fun getGeneralObjectives(): String? {
        return generalObjectives
    }

    fun setGeneralObjectives(generalObjectives: String?) {
        this.generalObjectives = generalObjectives
    }

    fun getTeachingMethod(): String? {
        return teachingMethod
    }

    fun setTeachingMethod(teachingMethod: String?) {
        this.teachingMethod = teachingMethod
    }

    fun getPreviousKnowledge(): String? {
        return previousKnowledge
    }

    fun setPreviousKnowledge(previousKnowledge: String?) {
        this.previousKnowledge = previousKnowledge
    }

    fun getComprehensiveQuestions(): String? {
        return comprehensiveQuestions
    }

    fun setComprehensiveQuestions(comprehensiveQuestions: String?) {
        this.comprehensiveQuestions = comprehensiveQuestions
    }

    fun getLectureYoutubeUrl(): String? {
        if (lectureYoutubeUrl == null)
            return ""
        return lectureYoutubeUrl
    }

    fun setLectureYoutubeUrl(lectureYoutubeUrl: String?) {
        this.lectureYoutubeUrl = lectureYoutubeUrl
    }

    fun getShowOnDate(): String? {
        return showOnDate
    }

    fun setShowOnDate(showOnDate: String?) {
        this.showOnDate = showOnDate
    }

    fun getCreatedBy(): String? {
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getPeriod(): String? {
        return period
    }

    fun setPeriod(period: String?) {
        this.period = period
    }

    fun getTeachingAids(): String? {
        return teachingAids
    }

    fun setTeachingAids(teachingAids: String?) {
        this.teachingAids = teachingAids
    }

    fun getPortionActuallyTaught(): String? {
        return portionActuallyTaught
    }

    fun setPortionActuallyTaught(portionActuallyTaught: String?) {
        this.portionActuallyTaught = portionActuallyTaught
    }

    fun getHwAssigned(): String? {
        return hwAssigned
    }

    fun setHwAssigned(hwAssigned: String?) {
        this.hwAssigned = hwAssigned
    }

    fun getHwNotAssignedReason(): String? {
        return hwNotAssignedReason
    }

    fun setHwNotAssignedReason(hwNotAssignedReason: String?) {
        this.hwNotAssignedReason = hwNotAssignedReason
    }

    fun getClass_(): String? {
        return _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getSection(): String? {
        return section
    }

    fun setSection(section: String?) {
        this.section = section
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}