package com.edulexa.activity.staff.student_profile.model.class_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClassData {
    private var selectFlag = false

    fun isSelectFlag(): Boolean {
        return selectFlag
    }

    fun setSelectFlag(selectFlag: Boolean) {
        this.selectFlag = selectFlag
    }

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("class")
    @Expose
    private var _class: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("start_date")
    @Expose
    private var startDate: String? = null

    @SerializedName("end_date")
    @Expose
    private var endDate: String? = null

    @SerializedName("edit_status")
    @Expose
    private var editStatus: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("in_time")
    @Expose
    private var inTime: String? = null

    @SerializedName("out_time")
    @Expose
    private var outTime: String? = null

    @SerializedName("half_day_time")
    @Expose
    private var halfDayTime: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getClass_(): String? {
        return if (_class == null) "null" else _class
    }

    fun setClass_(_class: String?) {
        this._class = _class
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getStartDate(): String? {
        return startDate
    }

    fun setStartDate(startDate: String?) {
        this.startDate = startDate
    }

    fun getEndDate(): String? {
        return endDate
    }

    fun setEndDate(endDate: String?) {
        this.endDate = endDate
    }

    fun getEditStatus(): String? {
        return editStatus
    }

    fun setEditStatus(editStatus: String?) {
        this.editStatus = editStatus
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

    fun getInTime(): String? {
        return inTime
    }

    fun setInTime(inTime: String?) {
        this.inTime = inTime
    }

    fun getOutTime(): String? {
        return outTime
    }

    fun setOutTime(outTime: String?) {
        this.outTime = outTime
    }

    fun getHalfDayTime(): String? {
        return halfDayTime
    }

    fun setHalfDayTime(halfDayTime: String?) {
        this.halfDayTime = halfDayTime
    }
}