package com.edulexa.activity.student.dashboard.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Attendance {
    @SerializedName("type")
    @Expose
    private var type: String? = null
    @SerializedName("in_time")
    @Expose
    private var inTime: String? = null
    @SerializedName("key_value")
    @Expose
    private var keyValue: String? = null
    @SerializedName("out_time")
    @Expose
    private var outTime: String? = null
    @SerializedName("point")
    @Expose
    private var point: String? = null

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun getInTime(): String? {
        return inTime
    }

    fun setInTime(inTime: String) {
        this.inTime = inTime
    }
    fun getKeyValue(): String? {
        return keyValue
    }

    fun setKeyValue(keyValue: String) {
        this.keyValue = keyValue
    }
    fun getOutTime(): String? {
        return outTime
    }

    fun setOutTime(outTime: String) {
        this.outTime = outTime
    }

    fun getPoint(): String? {
        return point
    }

    fun setPoint(point: String) {
        this.point = point
    }

}