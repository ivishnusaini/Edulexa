package com.edulexa.activity.student.attendance.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AttendanceData {
    @SerializedName("Absent")
    @Expose
    private var absent: Int? = null

    @SerializedName("Leave")
    @Expose
    private var leave: Int? = null

    @SerializedName("Present")
    @Expose
    private var present: Int? = null

    @SerializedName("Holiday")
    @Expose
    private var holiday: Int? = null

    @SerializedName("Late")
    @Expose
    private var late: Int? = null

    @SerializedName("Half Day")
    @Expose
    private var halfDay: Int? = null

    @SerializedName("attendance")
    @Expose
    private var attendance: List<Attendance?>? = null

    fun getAbsent(): Int? {
        return absent
    }

    fun setAbsent(absent: Int?) {
        this.absent = absent
    }

    fun getLeave(): Int? {
        return leave
    }

    fun setLeave(leave: Int?) {
        this.leave = leave
    }

    fun getPresent(): Int? {
        return present
    }

    fun setPresent(present: Int?) {
        this.present = present
    }

    fun getHoliday(): Int? {
        return holiday
    }

    fun setHoliday(holiday: Int?) {
        this.holiday = holiday
    }

    fun getLate(): Int? {
        return late
    }

    fun setLate(late: Int?) {
        this.late = late
    }

    fun getHalfDay(): Int? {
        return halfDay
    }

    fun setHalfDay(halfDay: Int?) {
        this.halfDay = halfDay
    }

    fun getAttendance(): List<Attendance?>? {
        return attendance
    }

    fun setAttendance(attendance: List<Attendance?>?) {
        this.attendance = attendance
    }

}