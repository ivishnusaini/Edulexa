package com.edulexa.activity.student.hostel.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DatumHostel {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("hostel_name")
    @Expose
    private var hostelName: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    fun getId(): String? {
        if (id == null)
            return ""
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getHostelName(): String? {
        if (hostelName == null)
            return ""
        return hostelName
    }

    fun setHostelName(hostelName: String?) {
        this.hostelName = hostelName
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }
}