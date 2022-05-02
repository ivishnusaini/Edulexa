package com.edulexa.activity.branch_code.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Branch {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("institute_name")
    @Expose
    private var instituteName: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("password")
    @Expose
    private var password: String? = null

    @SerializedName("address")
    @Expose
    private var address: String? = null

    @SerializedName("mobile_no")
    @Expose
    private var mobileNo: String? = null

    @SerializedName("db_name")
    @Expose
    private var dbName: String? = null

    @SerializedName("db_user")
    @Expose
    private var dbUser: String? = null

    @SerializedName("db_password")
    @Expose
    private var dbPassword: String? = null

    @SerializedName("role_id")
    @Expose
    private var roleId: String? = null

    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    fun getId(): String? {
        return if (id == null) "" else id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getInstituteName(): String? {
        return if (instituteName == null) "" else instituteName
    }

    fun setInstituteName(instituteName: String?) {
        this.instituteName = instituteName
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getAddress(): String? {
        return address
    }

    fun setAddress(address: String?) {
        this.address = address
    }

    fun getMobileNo(): String? {
        return mobileNo
    }

    fun setMobileNo(mobileNo: String?) {
        this.mobileNo = mobileNo
    }

    fun getDbName(): String? {
        return dbName
    }

    fun setDbName(dbName: String?) {
        this.dbName = dbName
    }

    fun getDbUser(): String? {
        return dbUser
    }

    fun setDbUser(dbUser: String?) {
        this.dbUser = dbUser
    }

    fun getDbPassword(): String? {
        return dbPassword
    }

    fun setDbPassword(dbPassword: String?) {
        this.dbPassword = dbPassword
    }

    fun getRoleId(): String? {
        return roleId
    }

    fun setRoleId(roleId: String?) {
        this.roleId = roleId
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

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }
}