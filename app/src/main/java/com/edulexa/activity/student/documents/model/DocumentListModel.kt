package com.edulexa.activity.student.documents.model

class DocumentListModel {
    var id = ""
    var folderName = ""
    var folderPath = ""
    var createdAt = ""
    var updatedAt = ""
    var status = ""

    @JvmName("getId1")
    fun getId(): String? {
        return id
    }

    @JvmName("setId1")
    fun setId(id: String?) {
        this.id = id!!
    }

    @JvmName("getFolderName1")
    fun getFolderName(): String? {
        return folderName
    }

    @JvmName("setFolderName1")
    fun setFolderName(folderName: String?) {
        this.folderName = folderName!!
    }

    @JvmName("getFolderPath1")
    fun getFolderPath(): String? {
        return folderPath
    }

    @JvmName("setFolderPath1")
    fun setFolderPath(folderPath: String?) {
        this.folderPath = folderPath!!
    }

    @JvmName("getCreatedAt1")
    fun getCreatedAt(): String? {
        return createdAt
    }

    @JvmName("setCreatedAt1")
    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt!!
    }

    @JvmName("getUpdatedAt1")
    fun getUpdatedAt(): String? {
        return updatedAt
    }

    @JvmName("setUpdatedAt1")
    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt!!
    }

    @JvmName("getStatus1")
    fun getStatus(): String? {
        return status
    }

    @JvmName("setStatus1")
    fun setStatus(status: String?) {
        this.status = status!!
    }
}