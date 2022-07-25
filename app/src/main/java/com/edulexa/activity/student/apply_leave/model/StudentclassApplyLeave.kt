package com.edulexa.activity.student.apply_leave.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class StudentclassApplyLeave {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("route_id")
    @Expose
    private var routeId: String? = null

    @SerializedName("hostel_room_id")
    @Expose
    private var hostelRoomId: String? = null

    @SerializedName("vehroute_id")
    @Expose
    private var vehrouteId: Any? = null

    @SerializedName("transport_fees")
    @Expose
    private var transportFees: String? = null

    @SerializedName("fees_discount")
    @Expose
    private var feesDiscount: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("is_alumni")
    @Expose
    private var isAlumni: String? = null

    @SerializedName("dis_reason")
    @Expose
    private var disReason: String? = null

    @SerializedName("dis_note")
    @Expose
    private var disNote: String? = null

    @SerializedName("is_disable")
    @Expose
    private var isDisable: String? = null

    @SerializedName("student_type")
    @Expose
    private var studentType: String? = null

    @SerializedName("disable_at")
    @Expose
    private var disableAt: Any? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getSessionId(): String? {
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
    }

    fun getStudentId(): String? {
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
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

    fun getRouteId(): String? {
        return routeId
    }

    fun setRouteId(routeId: String?) {
        this.routeId = routeId
    }

    fun getHostelRoomId(): String? {
        return hostelRoomId
    }

    fun setHostelRoomId(hostelRoomId: String?) {
        this.hostelRoomId = hostelRoomId
    }

    fun getVehrouteId(): Any? {
        return vehrouteId
    }

    fun setVehrouteId(vehrouteId: Any?) {
        this.vehrouteId = vehrouteId
    }

    fun getTransportFees(): String? {
        return transportFees
    }

    fun setTransportFees(transportFees: String?) {
        this.transportFees = transportFees
    }

    fun getFeesDiscount(): String? {
        return feesDiscount
    }

    fun setFeesDiscount(feesDiscount: String?) {
        this.feesDiscount = feesDiscount
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getIsAlumni(): String? {
        return isAlumni
    }

    fun setIsAlumni(isAlumni: String?) {
        this.isAlumni = isAlumni
    }

    fun getDisReason(): String? {
        return disReason
    }

    fun setDisReason(disReason: String?) {
        this.disReason = disReason
    }

    fun getDisNote(): String? {
        return disNote
    }

    fun setDisNote(disNote: String?) {
        this.disNote = disNote
    }

    fun getIsDisable(): String? {
        return isDisable
    }

    fun setIsDisable(isDisable: String?) {
        this.isDisable = isDisable
    }

    fun getStudentType(): String? {
        return studentType
    }

    fun setStudentType(studentType: String?) {
        this.studentType = studentType
    }

    fun getDisableAt(): Any? {
        return disableAt
    }

    fun setDisableAt(disableAt: Any?) {
        this.disableAt = disableAt
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

    fun getStudentSessionId(): String? {
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }
}