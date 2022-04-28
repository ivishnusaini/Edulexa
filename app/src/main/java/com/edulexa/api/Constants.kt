package com.edulexa.api

class Constants {
    companion object {
        val BASE_URL_SCHOOL_CODE = "https://edulexa.online/edulexa/staff_api/app/web_url"
        private const val BASE_URL_DOMAIN = ""
        val BASE_URL = BASE_URL_DOMAIN + ""
    }

    object ReportCardDetail {
        val TITLE = "title"
    }

    object StudentExamDetail {
        val TITLE = "title"
    }

    object AppSaveData {
        var gallerystudenttype = "all";
    }

    object Preference {
        const val IS_LOGIN = "is_login"
    }

    object Params {

        /*Header Params*/
        const val CLIENT_SERVICE = "Client-Service"
        const val AUTH_KEY = "Auth-Key"
        const val NEW_KEY = "New-Key"
        const val USER_ID = "User-Id"
        const val DB_ID = "Db-Id"
        /*Header Params*/


        const val WEB_CODE = "web_code"
    }

    object Apis {
        val LOGIN = BASE_URL + "login"
    }
}