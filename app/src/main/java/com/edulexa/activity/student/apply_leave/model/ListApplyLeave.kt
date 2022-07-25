package com.edulexa.activity.student.apply_leave.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ListApplyLeave {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("from_date")
    @Expose
    private var fromDate: String? = null

    @SerializedName("to_date")
    @Expose
    private var toDate: String? = null

    @SerializedName("apply_date")
    @Expose
    private var applyDate: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("docs")
    @Expose
    private var docs: String? = null

    @SerializedName("reason")
    @Expose
    private var reason: String? = null

    @SerializedName("approve_by")
    @Expose
    private var approveBy: String? = null

    @SerializedName("request_type")
    @Expose
    private var requestType: String? = null

    @SerializedName("firstname")
    @Expose
    private var firstname: String? = null

    @SerializedName("lastname")
    @Expose
    private var lastname: String? = null

    @SerializedName("staff_name")
    @Expose
    private var staffName: Any? = null

    @SerializedName("surname")
    @Expose
    private var surname: Any? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getStudentSessionId(): String? {
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }

    fun getFromDate(): String? {
        if (fromDate == null)
            return ""
        return fromDate
    }

    fun setFromDate(fromDate: String?) {
        this.fromDate = fromDate
    }

    fun getToDate(): String? {
        if (toDate == null)
            return ""
        return toDate
    }

    fun setToDate(toDate: String?) {
        this.toDate = toDate
    }

    fun getApplyDate(): String? {
        if (applyDate == null)
            return ""
        return applyDate
    }

    fun setApplyDate(applyDate: String?) {
        this.applyDate = applyDate
    }

    fun getStatus(): String? {
        if (status == null)
            return ""
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

    fun getDocs(): String? {
        if (docs == null)
            return ""
        return docs
    }

    fun setDocs(docs: String?) {
        this.docs = docs
    }

    fun getReason(): String? {
        if (reason == null)
            return ""
        return reason
    }

    fun setReason(reason: String?) {
        this.reason = reason
    }

    fun getApproveBy(): String? {
        return approveBy
    }

    fun setApproveBy(approveBy: String?) {
        this.approveBy = approveBy
    }

    fun getRequestType(): String? {
        return requestType
    }

    fun setRequestType(requestType: String?) {
        this.requestType = requestType
    }

    fun getFirstname(): String? {
        return firstname
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

    fun getStaffName(): Any? {
        return staffName
    }

    fun setStaffName(staffName: Any?) {
        this.staffName = staffName
    }

    fun getSurname(): Any? {
        return surname
    }

    fun setSurname(surname: Any?) {
        this.surname = surname
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

}