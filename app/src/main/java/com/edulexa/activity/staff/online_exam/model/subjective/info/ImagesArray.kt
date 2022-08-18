package com.edulexa.activity.staff.online_exam.model.subjective.info

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImagesArray {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("file_url")
    @Expose
    private var fileUrl: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getFileUrl(): String? {
        if (fileUrl!!.startsWith("/")) {
            val index = fileUrl!!.indexOf("/")
            return fileUrl!!.substring(index)
        }
        return fileUrl
    }

    fun setFileUrl(fileUrl: String?) {
        this.fileUrl = fileUrl
    }
}