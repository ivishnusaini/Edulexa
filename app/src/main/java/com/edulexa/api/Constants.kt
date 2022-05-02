package com.edulexa.api

class Constants {
    companion object {
        val BASE_URL_SCHOOL_CODE = "https://edulexa.online/edulexa/staff_api/app/web_url"

        /*Student Base Url and Value*/
        var BASE_URL_STUDENT = ""
        var DOMAIN_STUDENT = BASE_URL_STUDENT + "api"
        var APIURL_STUDENT = DOMAIN_STUDENT
        var PG_RETURN_URL_STUDENT = BASE_URL_STUDENT + "/api/Traknpay"
        var PG_RETURN_BULK_URL_STUDENT = BASE_URL_STUDENT + "/api/Traknpay/balkFeeAdd"
        var PG_RETURN_TRANSPORT_BULK_URL_STUDENT = BASE_URL_STUDENT + "/api/Traknpay/balkTransportFeeAdd"
        var BASEURL_WEBVIEW_STUDENT = BASE_URL_STUDENT + "/site/Userlogin?username="
        var SCHOOL_NAME = ""
        var BASE_URL_SCHOOL_LOGO = ""
        var IMAGESURL_STUDENT = BASE_URL_STUDENT

        val API = "api"
        val API_TRAKNPAY = "api/Traknpay"
        val API_TRAKNPAY_BALKFEEADD = "api/Traknpay/balkFeeAdd"
        val API_TRAKNPAY_BALKTRANSPORTFEEADD = "api/Traknpay/balkTransportFeeAdd"
        val SITE_USERLOGIN_USERNAME = "site/Userlogin?username="
        /*Student Base Url and Value*/


        /*Staff Base Url and Value*/
        var BASE_URL_WEBVIEW_DOMAIN_STAFF = ""
        var BASE_URL_STAFF = BASE_URL_WEBVIEW_DOMAIN_STAFF + "staff_api/webservice/"
        var BASE_URL_WEBVIEW_STAFF = BASE_URL_WEBVIEW_DOMAIN_STAFF + "site/webviewLogin?username="

        val STAFF_API_WEBSERVICE = "staff_api/webservice/"
        val SITE_WEBVIEWLOGIN_USERNAME = "site/webviewLogin?username="
        /*Staff Base Url and Value*/
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
        const val LOGOUTSTATUS = "logoutStatus"
        const val LOGOUTSTATUS_VALUE = "manual"
        const val LANG_CODE_STUDENT = "lang_code_student"
        const val STUDENT_IS_LOGIN = "student_is_login"
        const val STUDENT_IS_LOGIN_YES = "yes"
        const val APIURL_STUDENT = "apiUrl"
        const val IMAGESURL_STUDENT = "imagesUrl"

        /*Staff*/
        const val STAFF_BASE_URL = "staff_base_url"
        const val LANG_CODE_STAFF = "lang_code_staff"
        const val STAFF_IS_LOGIN = "staff_is_login"
        const val STAFF_IS_LOGIN_YES = "yes"
        const val BASE_URL_GET_OR_NOT = "base_url_get_or_not"
        const val BASE_URL_GET_OR_NOT_YES = "yes"


        /*App Used*/
        const val APP_TYPE = "app_type"
        const val APP_TYPE_STAFF = "staff"
        const val APP_TYPE_STUDENT = "student"
        const val FETCH_BASE_URL = "fetch_base_url"
        const val SCHOOL_LOGO = "school_logo"
        const val SCHOOL_NAME = "school_name"

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