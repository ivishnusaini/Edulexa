package com.edulexa.activity.staff.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class RolesStaff {
    @SerializedName("Super Admin")
    @Expose
    private var superAdmin: String? = null

    fun getSuperAdmin(): String? {
        return superAdmin
    }

    fun setSuperAdmin(superAdmin: String?) {
        this.superAdmin = superAdmin
    }
}