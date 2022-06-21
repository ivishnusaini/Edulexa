package com.edulexa.activity.student.dashboard.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashboardDatum {
    @SerializedName("module_list")
    @Expose
    private var moduleList: List<ModuleDashboard?>? = null

    @SerializedName("notification")
    @Expose
    private var notification: List<DatumNotification?>? = null

    @SerializedName("homeworklist")
    @Expose
    private var homeworklist: List<HomeworkStudentDashboard?>? = null

    fun getModuleList(): List<ModuleDashboard?>? {
        return moduleList
    }

    fun setModuleList(moduleList: List<ModuleDashboard?>?) {
        this.moduleList = moduleList
    }

    fun getNotification(): List<DatumNotification?>? {
        return notification
    }

    fun setNotification(notification: List<DatumNotification?>?) {
        this.notification = notification
    }

    fun getHomeworklist(): List<HomeworkStudentDashboard?>? {
        return homeworklist
    }

    fun setHomeworklist(homeworklist: List<HomeworkStudentDashboard?>?) {
        this.homeworklist = homeworklist
    }
}