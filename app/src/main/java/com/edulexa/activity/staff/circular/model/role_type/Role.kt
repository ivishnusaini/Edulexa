package com.edulexa.activity.staff.circular.model.role_type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Role {
    var selectRole = false

    fun isSelectRole(): Boolean {
        return selectRole
    }

    @JvmName("setSelectRole1")
    fun setSelectRole(selectRole: Boolean) {
        this.selectRole = selectRole
    }

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("slug")
    @Expose
    private var slug: Any? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("is_system")
    @Expose
    private var isSystem: String? = null

    @SerializedName("is_superadmin")
    @Expose
    private var isSuperadmin: String? = null

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

    fun getName(): String? {
        return if (name == null) "null" else name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getSlug(): Any? {
        return slug
    }

    fun setSlug(slug: Any?) {
        this.slug = slug
    }

    fun getIsActive(): String? {
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getIsSystem(): String? {
        return isSystem
    }

    fun setIsSystem(isSystem: String?) {
        this.isSystem = isSystem
    }

    fun getIsSuperadmin(): String? {
        return isSuperadmin
    }

    fun setIsSuperadmin(isSuperadmin: String?) {
        this.isSuperadmin = isSuperadmin
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