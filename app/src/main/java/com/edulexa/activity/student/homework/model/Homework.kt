package com.edulexa.activity.student.homework.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Homework {
    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("homeworklist")
    @Expose
    private var homeworklist: List<HomeworkData?>? = null

    fun getDate(): String? {
        if (date == null)
            return ""
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getHomeworklist(): List<HomeworkData?>? {
        return homeworklist
    }

    fun setHomeworklist(homeworklist: List<HomeworkData?>?) {
        this.homeworklist = homeworklist
    }
}