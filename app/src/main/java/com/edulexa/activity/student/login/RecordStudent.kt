package com.edulexa.activity.student.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.intellij.lang.annotations.Language


class RecordStudent {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null

    @SerializedName("role")
    @Expose
    private var role: String? = null

    @SerializedName("username")
    @Expose
    private var username: String? = null

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("section")
    @Expose
    private var section: String? = null

    @SerializedName("student_session_id")
    @Expose
    private var studentSessionId: String? = null

    @SerializedName("date_format")
    @Expose
    private var dateFormat: String? = null

    @SerializedName("currency_symbol")
    @Expose
    private var currencySymbol: String? = null

    @SerializedName("timezone")
    @Expose
    private var timezone: String? = null

    @SerializedName("sch_name")
    @Expose
    private var schName: String? = null

    @SerializedName("language")
    @Expose
    private var language: LanguageStudent? = null

    @SerializedName("is_rtl")
    @Expose
    private var isRtl: String? = null

    @SerializedName("theme")
    @Expose
    private var theme: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("roll_no")
    @Expose
    private var rollNo: String? = null

    @SerializedName("session_id")
    @Expose
    private var sessionId: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("parent_childs")
    @Expose
    private var parentChild: List<ParentChildList>? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getStudentId(): String? {
        if (studentId == null)
            return ""
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }

    fun getRole(): String? {
        return role
    }

    fun setRole(role: String?) {
        this.role = role
    }

    fun getUsername(): String? {
        if (username == null)
            return ""
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getClass_(): String? {
        if (_class == null)
            return ""
        return _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getSection(): String? {
        if (section == null)
            return ""
        return section
    }

    fun setSection(section: String?) {
        this.section = section
    }

    fun getStudentSessionId(): String? {
        if (studentSessionId == null)
            return ""
        return studentSessionId
    }

    fun setStudentSessionId(studentSessionId: String?) {
        this.studentSessionId = studentSessionId
    }

    fun getDateFormat(): String? {
        return dateFormat
    }

    fun setDateFormat(dateFormat: String?) {
        this.dateFormat = dateFormat
    }

    fun getCurrencySymbol(): String? {
        return currencySymbol
    }

    fun setCurrencySymbol(currencySymbol: String?) {
        this.currencySymbol = currencySymbol
    }

    fun getTimezone(): String? {
        return timezone
    }

    fun setTimezone(timezone: String?) {
        this.timezone = timezone
    }

    fun getSchName(): String? {
        return schName
    }

    fun setSchName(schName: String?) {
        this.schName = schName
    }

    fun getLanguage(): LanguageStudent? {
        return language
    }

    fun setLanguage(language: LanguageStudent?) {
        this.language = language
    }

    fun getIsRtl(): String? {
        return isRtl
    }

    fun setIsRtl(isRtl: String?) {
        this.isRtl = isRtl
    }

    fun getTheme(): String? {
        return theme
    }

    fun setTheme(theme: String?) {
        this.theme = theme
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getRollNo(): String? {
        return rollNo
    }

    fun setRollNo(rollNo: String?) {
        this.rollNo = rollNo
    }

    fun getSessionId(): String? {
        if (sessionId == null)
            return ""
        return sessionId
    }

    fun setSessionId(sessionId: String?) {
        this.sessionId = sessionId
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
    fun getParentChildList(): List<ParentChildList>? {
        return parentChild
    }

    fun setParentChildList(parentChild: List<ParentChildList>?) {
        this.parentChild = parentChild
    }

}