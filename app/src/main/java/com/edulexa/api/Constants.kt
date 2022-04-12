package com.edulexa.api

class Constants {
    companion object{
        private const val BASE_URL_DOMAIN = ""
        val BASE_URL = BASE_URL_DOMAIN + ""
    }
    object ReportCardDetail{
        val TITLE = "title"
    }
    object AppSaveData {
        var gallerystudenttype = "all";
    }

    object Preference {
        const val IS_LOGIN = "is_login"
    }
    object Params {
        const val DEVICE_ID = "device_id"
    }
    object Apis {
        val LOGIN = BASE_URL + "login"
    }
}