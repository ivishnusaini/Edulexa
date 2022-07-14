package com.edulexa.activity.student.library.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class LibraryResponse {
    @SerializedName("bookCount")
    @Expose
    private var bookCount: String? = null

    @SerializedName("return")
    @Expose
    private var _return: String? = null

    @SerializedName("issueBook")
    @Expose
    private var issueBook: List<IssueBook?>? = null

    fun getBookCount(): String? {
        return bookCount
    }

    fun setBookCount(bookCount: String?) {
        this.bookCount = bookCount
    }

    fun getReturn(): String? {
        return _return
    }

    fun setReturn(_return: String?) {
        this._return = _return
    }

    fun getIssueBook(): List<IssueBook?>? {
        return issueBook
    }

    fun setIssueBook(issueBook: List<IssueBook?>?) {
        this.issueBook = issueBook
    }
}