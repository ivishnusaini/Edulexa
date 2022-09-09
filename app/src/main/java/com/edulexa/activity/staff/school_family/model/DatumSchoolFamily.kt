package com.edulexa.activity.staff.school_family.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DatumSchoolFamily {
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
    private var department: Any? = null

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
        return if (name == null) "null" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getSurname(): String? {
        return if (surname == null) "null" else surname
    }

    fun setSurname(surname: String?) {
        this.surname = surname
    }

    fun getImage(): String? {
        return if (image == null) "" else image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getDesignation(): String? {
        return if (designation == null) "null" else designation
    }

    fun setDesignation(designation: String?) {
        this.designation = designation
    }

    fun getDepartment(): Any? {
        return department
    }

    fun setDepartment(department: Any?) {
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