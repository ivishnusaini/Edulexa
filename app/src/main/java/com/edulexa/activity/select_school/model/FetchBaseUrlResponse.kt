package com.edulexa.activity.select_school.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FetchBaseUrlResponse {
    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("url")
    @Expose
    private var url: Url? = null

    @SerializedName("school_image")
    @Expose
    private var schoolImage: Any? = null

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getUrl(): Url? {
        return url
    }

    fun setUrl(url: Url?) {
        this.url = url
    }

    fun getSchoolImage(): Any? {
        return schoolImage
    }

    fun setSchoolImage(schoolImage: Any?) {
        this.schoolImage = schoolImage
    }
}