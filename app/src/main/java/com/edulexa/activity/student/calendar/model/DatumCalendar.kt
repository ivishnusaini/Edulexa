package com.edulexa.activity.student.calendar.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumCalendar {
    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("desciption")
    @Expose
    private var desciption: String? = null

    @SerializedName("color")
    @Expose
    private var color: String? = null

    fun getDate(): String? {
        if (date == null)
            return ""
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getDesciption(): String? {
        if (desciption == null)
            return ""
        return desciption
    }

    fun setDesciption(desciption: String?) {
        this.desciption = desciption
    }

    fun getColor(): String? {
        return color
    }

    fun setColor(color: String?) {
        this.color = color
    }
}