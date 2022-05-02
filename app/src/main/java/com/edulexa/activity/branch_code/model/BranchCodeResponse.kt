package com.edulexa.activity.branch_code.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BranchCodeResponse {
    @SerializedName("branch")
    @Expose
    private var branch: List<Branch?>? = null

    @SerializedName("status")
    @Expose
    private var status: Int? = null

    fun getBranch(): List<Branch?>? {
        return if (branch == null) ArrayList() else branch
    }

    fun setBranch(branch: List<Branch?>?) {
        this.branch = branch
    }

    fun getStatus(): Int? {
        return if (status == null) 400 else status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }
}