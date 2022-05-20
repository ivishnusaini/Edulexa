package com.edulexa.activity.student.login

import android.icu.text.AlphabeticIndex.Record
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class StudentLoginResponse {
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

    @SerializedName("record")
    @Expose
    private var record: RecordStudent? = null

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
        if (token == null)
            return ""
        return token
    }

    fun setToken(token: String?) {
        this.token = token
    }

    fun getRole(): String? {
        if (role == null)
            return ""
        return role
    }

    fun setRole(role: String?) {
        this.role = role
    }

    fun getRecord(): RecordStudent? {
        return record
    }

    fun setRecord(record: RecordStudent?) {
        this.record = record
    }

}