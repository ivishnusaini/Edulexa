package com.edulexa.activity.staff.homework.model.evaluation

import com.edulexa.api.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubmitDoc {
    @SerializedName("file_url")
    @Expose
    private var fileUrl: String? = null

    fun getFileUrl(): String? {
        return if (fileUrl == null) "" else Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF + fileUrl
    }

    fun setFileUrl(fileUrl: String?) {
        this.fileUrl = fileUrl
    }
}