package com.edulexa.activity.staff.dashboard.model.notifications

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PermissionArray {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("short_code")
    @Expose
    private var shortCode: String? = null

    @SerializedName("perm_group_id")
    @Expose
    private var permGroupId: String? = null

    @SerializedName("can_view")
    @Expose
    private var canView: String? = null

    @SerializedName("can_edit")
    @Expose
    private var canEdit: String? = null

    @SerializedName("can_add")
    @Expose
    private var canAdd: String? = null

    @SerializedName("can_delete")
    @Expose
    private var canDelete: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getShortCode(): String? {
        return shortCode
    }

    fun setShortCode(shortCode: String?) {
        this.shortCode = shortCode
    }

    fun getPermGroupId(): String? {
        return permGroupId
    }

    fun setPermGroupId(permGroupId: String?) {
        this.permGroupId = permGroupId
    }

    fun getCanView(): String? {
        return canView
    }

    fun setCanView(canView: String?) {
        this.canView = canView
    }

    fun getCanEdit(): String? {
        return canEdit
    }

    fun setCanEdit(canEdit: String?) {
        this.canEdit = canEdit
    }

    fun getCanAdd(): String? {
        return canAdd
    }

    fun setCanAdd(canAdd: String?) {
        this.canAdd = canAdd
    }

    fun getCanDelete(): String? {
        return canDelete
    }

    fun setCanDelete(canDelete: String?) {
        this.canDelete = canDelete
    }
}