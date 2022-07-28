package com.edulexa.activity.student.dashboard.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LiveClassConfig {
    @SerializedName("api_key")
    @Expose
    private var apiKey: String? = null

    @SerializedName("api_secret")
    @Expose
    private var apiSecret: String? = null

    fun getApiKey(): String? {
        if (apiKey == null)
            return ""
        return apiKey
    }

    fun setApiKey(apiKey: String?) {
        this.apiKey = apiKey
    }

    fun getApiSecret(): String? {
        if (apiSecret == null)
            return ""
        return apiSecret
    }

    fun setApiSecret(apiSecret: String?) {
        this.apiSecret = apiSecret
    }
}