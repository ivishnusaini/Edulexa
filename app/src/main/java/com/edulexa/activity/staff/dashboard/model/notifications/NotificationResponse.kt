package com.edulexa.activity.staff.dashboard.model.notifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NotificationResponse {
    @SerializedName("notification")
    @Expose
    private var notification: Notification? = null

    @SerializedName("permission_array")
    @Expose
    private var permissionArray: List<PermissionArray?>? = null

    fun getNotification(): Notification? {
        return notification
    }

    fun setNotification(notification: Notification?) {
        this.notification = notification
    }

    fun getPermissionArray(): List<PermissionArray?>? {
        return permissionArray
    }

    fun setPermissionArray(permissionArray: List<PermissionArray?>?) {
        this.permissionArray = permissionArray
    }
}