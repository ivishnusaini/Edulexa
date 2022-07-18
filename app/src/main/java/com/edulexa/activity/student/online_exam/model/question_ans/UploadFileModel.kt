package com.edulexa.activity.student.online_exam.model.question_ans

import java.io.File

class UploadFileModel(file: File,upload : Boolean) {
    var file: File? = null
    var upload = false
    init {
        this.file = file
        this.upload = upload
    }

    @JvmName("getFile1")
    fun getFile(): File? {
        return file
    }

    @JvmName("setFile1")
    fun setFile(file: File?) {
        this.file = file
    }

    fun isUpload(): Boolean {
        return upload
    }

    @JvmName("setUpload1")
    fun setUpload(upload: Boolean) {
        this.upload = upload
    }
}