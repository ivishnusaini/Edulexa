package com.edulexa.activity.staff.lesson_plan.model.list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultLessonPlan {
    @SerializedName("timetable")
    @Expose
    private var timetable: List<TimetableLessonPlan?>? = null

    @SerializedName("staff_id")
    @Expose
    private var staffId: String? = null

    fun getTimetable(): List<TimetableLessonPlan?>? {
        if (timetable == null)
            return ArrayList()
        return timetable
    }

    fun setTimetable(timetable: List<TimetableLessonPlan?>?) {
        this.timetable = timetable
    }

    fun getStaffId(): String? {
        return staffId
    }

    fun setStaffId(staffId: String?) {
        this.staffId = staffId
    }
}