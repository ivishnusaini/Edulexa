package com.edulexa.activity.student.login

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class LanguageStudent {
    @SerializedName("lang_id")
    @Expose
    private var langId: String? = null

    @SerializedName("language")
    @Expose
    private var language: String? = null

    fun getLangId(): String? {
        return langId
    }

    fun setLangId(langId: String?) {
        this.langId = langId
    }

    fun getLanguage(): String? {
        return language
    }

    fun setLanguage(language: String?) {
        this.language = language
    }
}