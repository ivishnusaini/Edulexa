package com.edulexa.activity.staff.homework.model.homeworklist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class HomeworkResponse {
    @SerializedName("homeworklist")
    @Expose
    private var homeworklist: List<Homework?>? = null

    @SerializedName("staff_role")
    @Expose
    private var staffRole: String? = null

    @SerializedName("permission_array")
    @Expose
    private var permissionArray: List<PermissionArray?>? = null

    fun getHomeworklist(): List<Homework?>? {
        if (homeworklist == null)
            return ArrayList()
        return homeworklist
    }

    fun setHomeworklist(homeworklist: List<Homework?>?) {
        this.homeworklist = homeworklist
    }

    fun getStaffRole(): String? {
        return staffRole
    }

    fun setStaffRole(staffRole: String?) {
        this.staffRole = staffRole
    }

    fun getPermissionArray(): List<PermissionArray?>? {
        return permissionArray
    }

    fun setPermissionArray(permissionArray: List<PermissionArray?>?) {
        this.permissionArray = permissionArray
    }
}