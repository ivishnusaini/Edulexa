package com.edulexa.activity.student.documents.model.document_folder_detail

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumFolderDetail {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("student_id")
    @Expose
    private var studentId: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("doc")
    @Expose
    private var doc: String? = null

    @SerializedName("folder_id")
    @Expose
    private var folderId: String? = null

    @SerializedName("image_name")
    @Expose
    private var imageName: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getStudentId(): String? {
        return studentId
    }

    fun setStudentId(studentId: String?) {
        this.studentId = studentId
    }

    fun getTitle(): String? {
        if (title == null)
            return ""
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDoc(): String? {
        if (doc == null)
            return ""
        return doc
    }

    fun setDoc(doc: String?) {
        this.doc = doc
    }

    fun getFolderId(): String? {
        return folderId
    }

    fun setFolderId(folderId: String?) {
        this.folderId = folderId
    }

    fun getImageName(): String? {
        if (imageName == null)
            return ""
        return imageName
    }

    fun setImageName(imageName: String?) {
        this.imageName = imageName
    }
}