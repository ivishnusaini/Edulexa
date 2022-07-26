package com.edulexa.activity.student.live_classes.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class DatumLiveClass {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("type_title")
    @Expose
    private var typeTitle: String? = null

    @SerializedName("subject")
    @Expose
    private var subject: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("video_url")
    @Expose
    private var videoUrl: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("cls_sec_id")
    @Expose
    private var clsSecId: String? = null

    @SerializedName("descs")
    @Expose
    private var descs: String? = null

    @SerializedName("fdate")
    @Expose
    private var fdate: String? = null

    @SerializedName("tdate")
    @Expose
    private var tdate: String? = null

    @SerializedName("check_all")
    @Expose
    private var checkAll: String? = null

    @SerializedName("from_time")
    @Expose
    private var fromTime: String? = null

    @SerializedName("to_time")
    @Expose
    private var toTime: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("passcode")
    @Expose
    private var passcode: String? = null

    @SerializedName("meeting_id")
    @Expose
    private var meetingId: String? = null

    @SerializedName("participant_id")
    @Expose
    private var participantId: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getType(): String? {
        if (type == null)
            return ""
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getTypeTitle(): String? {
        return typeTitle
    }

    fun setTypeTitle(typeTitle: String?) {
        this.typeTitle = typeTitle
    }

    fun getSubject(): String? {
        return subject
    }

    fun setSubject(subject: String?) {
        this.subject = subject
    }

    fun getTitle(): String? {
        if (title == null)
            return ""
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getVideoUrl(): String? {
        if (videoUrl == null)
            return ""
        return videoUrl
    }

    fun setVideoUrl(videoUrl: String?) {
        this.videoUrl = videoUrl
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

    fun getDescs(): String? {
        if (descs == null)
            return ""
        return descs
    }

    fun setDescs(descs: String?) {
        this.descs = descs
    }

    fun getFdate(): String? {
        if (fdate == null)
            return ""
        return fdate
    }

    fun setFdate(fdate: String?) {
        this.fdate = fdate
    }

    fun getTdate(): String? {
        if (tdate == null)
            return ""
        return tdate
    }

    fun setTdate(tdate: String?) {
        this.tdate = tdate
    }

    fun getCheckAll(): String? {
        return checkAll
    }

    fun setCheckAll(checkAll: String?) {
        this.checkAll = checkAll
    }

    fun getFromTime(): String? {
        return fromTime
    }

    fun setFromTime(fromTime: String?) {
        this.fromTime = fromTime
    }

    fun getToTime(): String? {
        return toTime
    }

    fun setToTime(toTime: String?) {
        this.toTime = toTime
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getCreatedBy(): String? {
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
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

    fun getPasscode(): String? {
        if (passcode == null)
            return ""
        return passcode
    }

    fun setPasscode(passcode: String?) {
        this.passcode = passcode
    }

    fun getMeetingId(): String? {
        if (meetingId == null)
            return ""
        return meetingId
    }

    fun setMeetingId(meetingId: String?) {
        this.meetingId = meetingId
    }

    fun getParticipantId(): String? {
        return participantId
    }

    fun setParticipantId(participantId: String?) {
        this.participantId = participantId
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

}