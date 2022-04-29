package com.edulexa.api

class Constants {
    companion object {
        val BASE_URL_SCHOOL_CODE = "https://edulexa.online/edulexa/staff_api/app/web_url"

        var BASE_URL_STUDENT = ""
        var DOMAIN_STUDENT = BASE_URL_STUDENT + "api"
        var APIURL_STUDENT = DOMAIN_STUDENT
        var PG_RETURN_URL_STUDENT = BASE_URL_STUDENT + "/api/Traknpay"
        var PG_RETURN_BULK_URL_STUDENT = BASE_URL_STUDENT + "/api/Traknpay/balkFeeAdd"
        var PG_RETURN_TRANSPORT_BULK_URL_STUDENT = BASE_URL_STUDENT + "/api/Traknpay/balkTransportFeeAdd"
        var BASEURL_WEBVIEW_STUDENT = BASE_URL_STUDENT + "/site/Userlogin?username="
        var BASE_URL_SCHOOL_LOGO_STUDENT = ""
        var IMAGESURL_STUDENT = BASE_URL_STUDENT

        var BASE_URL_WEBVIEW_DOMAIN_STAFF = ""
        val BASE_URL_STAFF = BASE_URL_WEBVIEW_DOMAIN_STAFF + "staff_api/webservice/"
        val BASE_URL_WEBVIEW_STAFF = BASE_URL_WEBVIEW_DOMAIN_STAFF + "site/webviewLogin?username="
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
        /*Student*/
        const val STUDENT_BASE_URL = "student_base_url"
        const val BACKGROUND_IMAGE = "background_image"
        const val SCHOOL_NAME = "school_name"
        const val LOGOUTSTATUS = "logoutStatus"
        const val LOGOUTSTATUS_VALUE = "manual"
        const val SCHOOL_LOGO = "school_logo"

        /*Staff*/
        const val BASE_URL_WEBVIEW_DOMAIN = ""
        const val STAFF_BASE_URL = "staff_base_url"
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

    object ApisStudent {
        val LOGIN = BASE_URL_STUDENT + "login"
    }
}