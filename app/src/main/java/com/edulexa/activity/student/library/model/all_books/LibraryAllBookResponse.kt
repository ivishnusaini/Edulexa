package com.edulexa.activity.student.library.model.all_books

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class LibraryAllBookResponse {
    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumAllBooks?>? = null

    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getData(): List<DatumAllBooks?>? {
        return data
    }

    fun setData(data: List<DatumAllBooks?>?) {
        this.data = data
    }
}