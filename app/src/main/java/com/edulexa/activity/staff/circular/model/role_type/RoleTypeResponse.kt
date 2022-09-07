package com.edulexa.activity.staff.circular.model.role_type

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RoleTypeResponse {

    @SerializedName("roles")
    @Expose
    private var roles: List<Role?>? = null

    @SerializedName("classlist")
    @Expose
    private var classlist: List<ClassListRole?>? = null

    fun getRoles(): List<Role?>? {
        if (roles == null)
            return ArrayList()
        return roles
    }

    fun setRoles(roles: List<Role?>?) {
        this.roles = roles
    }

    fun getClasslist(): List<ClassListRole?>? {
        if (classlist == null)
            return ArrayList()
        return classlist
    }

    fun setClasslist(classlist: List<ClassListRole?>?) {
        this.classlist = classlist
    }
}