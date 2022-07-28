package com.edulexa.activity.student.documents.model.document_folder_detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName



class DocumentFolderDetailResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumFolderDetail?>? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): List<DatumFolderDetail?>? {
        return data
    }

    fun setData(data: List<DatumFolderDetail?>?) {
        this.data = data
    }
}