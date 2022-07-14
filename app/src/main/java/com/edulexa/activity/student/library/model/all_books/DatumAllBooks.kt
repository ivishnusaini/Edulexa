package com.edulexa.activity.student.library.model.all_books

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumAllBooks {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("book_title")
    @Expose
    private var bookTitle: String? = null

    @SerializedName("book_no")
    @Expose
    private var bookNo: String? = null

    @SerializedName("isbn_no")
    @Expose
    private var isbnNo: String? = null

    @SerializedName("subject")
    @Expose
    private var subject: String? = null

    @SerializedName("rack_no")
    @Expose
    private var rackNo: String? = null

    @SerializedName("publish")
    @Expose
    private var publish: String? = null

    @SerializedName("author")
    @Expose
    private var author: String? = null

    @SerializedName("qty")
    @Expose
    private var qty: String? = null

    @SerializedName("perunitcost")
    @Expose
    private var perunitcost: String? = null

    @SerializedName("postdate")
    @Expose
    private var postdate: String? = null

    @SerializedName("description")
    @Expose
    private var description: String? = null

    @SerializedName("available")
    @Expose
    private var available: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("book_type")
    @Expose
    private var bookType: String? = null

    @SerializedName("is_view")
    @Expose
    private var isView: Any? = null

    @SerializedName("is_download")
    @Expose
    private var isDownload: Any? = null

    @SerializedName("cover_pic")
    @Expose
    private var coverPic: String? = null

    @SerializedName("document")
    @Expose
    private var document: Any? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getBookTitle(): String? {
        if (bookTitle == null)
            return ""
        return bookTitle
    }

    fun setBookTitle(bookTitle: String?) {
        this.bookTitle = bookTitle
    }

    fun getBookNo(): String? {
        return bookNo
    }

    fun setBookNo(bookNo: String?) {
        this.bookNo = bookNo
    }

    fun getIsbnNo(): String? {
        return isbnNo
    }

    fun setIsbnNo(isbnNo: String?) {
        this.isbnNo = isbnNo
    }

    fun getSubject(): String? {
        return subject
    }

    fun setSubject(subject: String?) {
        this.subject = subject
    }

    fun getRackNo(): String? {
        return rackNo
    }

    fun setRackNo(rackNo: String?) {
        this.rackNo = rackNo
    }

    fun getPublish(): String? {
        if (publish == null)
            return ""
        return publish
    }

    fun setPublish(publish: String?) {
        this.publish = publish
    }

    fun getAuthor(): String? {
        if (author == null)
            return ""
        return author
    }

    fun setAuthor(author: String?) {
        this.author = author
    }

    fun getQty(): String? {
        return qty
    }

    fun setQty(qty: String?) {
        this.qty = qty
    }

    fun getPerunitcost(): String? {
        return perunitcost
    }

    fun setPerunitcost(perunitcost: String?) {
        this.perunitcost = perunitcost
    }

    fun getPostdate(): String? {
        return postdate
    }

    fun setPostdate(postdate: String?) {
        this.postdate = postdate
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getAvailable(): String? {
        return available
    }

    fun setAvailable(available: String?) {
        this.available = available
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getBookType(): String? {
        return bookType
    }

    fun setBookType(bookType: String?) {
        this.bookType = bookType
    }

    fun getIsView(): Any? {
        return isView
    }

    fun setIsView(isView: Any?) {
        this.isView = isView
    }

    fun getIsDownload(): Any? {
        return isDownload
    }

    fun setIsDownload(isDownload: Any?) {
        this.isDownload = isDownload
    }

    fun getCoverPic(): String? {
        return coverPic
    }

    fun setCoverPic(coverPic: String?) {
        this.coverPic = coverPic
    }

    fun getDocument(): Any? {
        return document
    }

    fun setDocument(document: Any?) {
        this.document = document
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String?) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String?) {
        this.updatedAt = updatedAt
    }
}