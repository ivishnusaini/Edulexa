package com.edulexa.activity.select_school.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Url {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("web_code")
    @Expose
    private var webCode: String? = null

    @SerializedName("url")
    @Expose
    private var url: String? = null

    @SerializedName("school_image")
    @Expose
    private var schoolImage: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("background_image")
    @Expose
    private var backgroundImage: String? = null

    @SerializedName("school_name")
    @Expose
    private var schoolName: String? = null

    fun getSchoolName(): String? {
        return if (schoolName == null) "null" else schoolName
    }

    fun setSchoolName(schoolName: String?) {
        this.schoolName = schoolName
    }

    fun getBackgroundImage(): String? {
        return if (backgroundImage == null) "" else backgroundImage
    }

    fun setBackgroundImage(backgroundImage: String?) {
        this.backgroundImage = backgroundImage
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getWebCode(): String? {
        return webCode
    }

    fun setWebCode(webCode: String?) {
        this.webCode = webCode
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    fun getSchoolImage(): String? {
        return schoolImage
    }

    fun setSchoolImage(schoolImage: String?) {
        this.schoolImage = schoolImage
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }
}