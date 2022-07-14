package com.edulexa.activity.student.library.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class IssueBook {
    @SerializedName("return_date")
    @Expose
    private var returnDate: String? = null

    @SerializedName("book_no")
    @Expose
    private var bookNo: String? = null

    @SerializedName("issue_date")
    @Expose
    private var issueDate: String? = null

    @SerializedName("is_returned")
    @Expose
    private var isReturned: String? = null

    @SerializedName("book_title")
    @Expose
    private var bookTitle: String? = null

    @SerializedName("author")
    @Expose
    private var author: String? = null

    @SerializedName("subject")
    @Expose
    private var subject: String? = null

    fun getReturnDate(): String? {
        if (returnDate == null)
            return ""
        return returnDate
    }

    fun setReturnDate(returnDate: String?) {
        this.returnDate = returnDate
    }

    fun getBookNo(): String? {
        return bookNo
    }

    fun setBookNo(bookNo: String?) {
        this.bookNo = bookNo
    }

    fun getIssueDate(): String? {
        if (issueDate == null)
            return ""
        return issueDate
    }

    fun setIssueDate(issueDate: String?) {
        this.issueDate = issueDate
    }

    fun getIsReturned(): String? {
        return isReturned
    }

    fun setIsReturned(isReturned: String?) {
        this.isReturned = isReturned
    }

    fun getBookTitle(): String? {
        if (bookTitle == null)
            return ""
        return bookTitle
    }

    fun setBookTitle(bookTitle: String?) {
        this.bookTitle = bookTitle
    }

    fun getAuthor(): String? {
        if (author == null)
            return ""
        return author
    }

    fun setAuthor(author: String?) {
        this.author = author
    }

    fun getSubject(): String? {
        return subject
    }

    fun setSubject(subject: String?) {
        this.subject = subject
    }
}