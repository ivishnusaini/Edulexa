package com.edulexa.activity.student.teacher_rating.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class DatumTeacherList {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("contact_no")
    @Expose
    private var contactNo: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("surname")
    @Expose
    private var surname: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("designation")
    @Expose
    private var designation: String? = null

    @SerializedName("department")
    @Expose
    private var department: String? = null

    @SerializedName("user_type")
    @Expose
    private var userType: String? = null

    @SerializedName("image_path")
    @Expose
    private var imagePath: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getContactNo(): String? {
        return contactNo
    }

    fun setContactNo(contactNo: String?) {
        this.contactNo = contactNo
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getSurname(): String? {
        if (surname == null)
            return ""
        return surname
    }

    fun setSurname(surname: String?) {
        this.surname = surname
    }

    fun getImage(): String? {
        if (image == null)
            return ""
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getDesignation(): String? {
        if (designation == null)
            return ""
        return designation
    }

    fun setDesignation(designation: String?) {
        this.designation = designation
    }

    fun getDepartment(): String? {
        return department
    }

    fun setDepartment(department: String?) {
        this.department = department
    }

    fun getUserType(): String? {
        return userType
    }

    fun setUserType(userType: String?) {
        this.userType = userType
    }

    fun getImagePath(): String? {
        if (imagePath == null)
            return ""
        return imagePath
    }

    fun setImagePath(imagePath: String?) {
        this.imagePath = imagePath
    }
}