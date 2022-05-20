package com.edulexa.activity.student.dashboard.model

import `in`.aabhasjindal.otptextview.Utils
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumNotification {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("publish_date")
    @Expose
    private var publishDate: String? = null

    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("visible_student")
    @Expose
    private var visibleStudent: String? = null

    @SerializedName("visible_staff")
    @Expose
    private var visibleStaff: String? = null

    @SerializedName("visible_parent")
    @Expose
    private var visibleParent: String? = null

    @SerializedName("document")
    @Expose
    private var document: String? = null

    @SerializedName("is_individual")
    @Expose
    private var isIndividual: String? = null

    @SerializedName("user_list")
    @Expose
    private var userList: String? = null

    @SerializedName("is_class")
    @Expose
    private var isClass: String? = null

    @SerializedName("class_id")
    @Expose
    private var classId: String? = null

    @SerializedName("section_id")
    @Expose
    private var sectionId: String? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: String? = null

    @SerializedName("created_id")
    @Expose
    private var createdId: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getPublishDate(): String? {
        if (publishDate == null)
            return ""
        return publishDate
    }

    fun setPublishDate(publishDate: String?) {
        this.publishDate = publishDate
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getMessage(): String? {
        if (message == null)
            return ""
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getVisibleStudent(): String? {
        return visibleStudent
    }

    fun setVisibleStudent(visibleStudent: String?) {
        this.visibleStudent = visibleStudent
    }

    fun getVisibleStaff(): String? {
        return visibleStaff
    }

    fun setVisibleStaff(visibleStaff: String?) {
        this.visibleStaff = visibleStaff
    }

    fun getVisibleParent(): String? {
        return visibleParent
    }

    fun setVisibleParent(visibleParent: String?) {
        this.visibleParent = visibleParent
    }

    fun getDocument(): String? {
        return document
    }

    fun setDocument(document: String?) {
        this.document = document
    }

    fun getIsIndividual(): String? {
        return isIndividual
    }

    fun setIsIndividual(isIndividual: String?) {
        this.isIndividual = isIndividual
    }

    fun getUserList(): String? {
        return userList
    }

    fun setUserList(userList: String?) {
        this.userList = userList
    }

    fun getIsClass(): String? {
        return isClass
    }

    fun setIsClass(isClass: String?) {
        this.isClass = isClass
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

    fun getCreatedBy(): String? {
        return createdBy
    }

    fun setCreatedBy(createdBy: String?) {
        this.createdBy = createdBy
    }

    fun getCreatedId(): String? {
        return createdId
    }

    fun setCreatedId(createdId: String?) {
        this.createdId = createdId
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
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
}