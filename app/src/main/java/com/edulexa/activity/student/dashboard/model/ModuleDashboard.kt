package com.edulexa.activity.student.dashboard.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ModuleDashboard {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("short_code")
    @Expose
    private var shortCode: String? = null

    @SerializedName("is_active")
    @Expose
    private var isActive: String? = null

    @SerializedName("icon_link")
    @Expose
    private var iconLink: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        if (name == null)
            return ""
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getShortCode(): String? {
        if (shortCode == null)
            return ""
        return shortCode
    }

    fun setShortCode(shortCode: String?) {
        this.shortCode = shortCode
    }

    fun getIsActive(): String? {
        if (isActive == null)
            return ""
        return isActive
    }

    fun setIsActive(isActive: String?) {
        this.isActive = isActive
    }

    fun getIconLink(): String? {
        if (iconLink == null)
            return ""
        return iconLink
    }

    fun setIconLink(iconLink: String?) {
        this.iconLink = iconLink
    }
}