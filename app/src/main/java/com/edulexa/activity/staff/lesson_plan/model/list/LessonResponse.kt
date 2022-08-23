package com.edulexa.activity.staff.lesson_plan.model.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LessonResponse {
    @SerializedName("result")
    @Expose
    private var result: ResultLessonPlan? = null

    @SerializedName("permission_array")
    @Expose
    private var permissionArray: List<PermissionArray?>? = null

    fun getResult(): ResultLessonPlan? {
        return result
    }

    fun setResult(result: ResultLessonPlan?) {
        this.result = result
    }

    fun getPermissionArray(): List<PermissionArray?>? {
        return permissionArray
    }

    fun setPermissionArray(permissionArray: List<PermissionArray?>?) {
        this.permissionArray = permissionArray
    }
}