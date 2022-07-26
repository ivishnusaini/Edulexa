package com.edulexa.activity.student.live_classes.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class YoutubeList {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumLiveClass?>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumLiveClass?>? {
        return data
    }

    fun setData(data: List<DatumLiveClass?>?) {
        this.data = data
    }
}