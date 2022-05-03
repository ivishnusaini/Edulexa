package com.edulexa.activity.staff.login

import android.icu.text.AlphabeticIndex.Record
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StaffLoginResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("token")
    @Expose
    private var token: String? = null

    @SerializedName("role")
    @Expose
    private var role: String? = null

    @SerializedName("role_id")
    @Expose
    private var roleId: String? = null

    @SerializedName("record")
    @Expose
    private var record: RecordStaff? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(token: String?) {
        this.token = token
    }

    fun getRole(): String? {
        return role
    }

    fun setRole(role: String?) {
        this.role = role
    }

    fun getRoleId(): String? {
        return roleId
    }

    fun setRoleId(roleId: String?) {
        this.roleId = roleId
    }

    fun getRecord(): RecordStaff? {
        return record
    }

    fun setRecord(record: RecordStaff?) {
        this.record = record
    }
}