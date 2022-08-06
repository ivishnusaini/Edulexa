package com.edulexa.api

import com.edulexa.activity.student.chat.model.group.GroupUser
import com.edulexa.activity.student.live_classes.model.DatumLiveClass

class Constants {
    companion object {
        val BASE_URL_SCHOOL_CODE = "https://edulexa.online/beta/staff_api/app/web_url"

        /*Student Base Url and Value*/
        var BASE_URL_STUDENT = ""
        var DOMAIN_STUDENT = BASE_URL_STUDENT + "api"
        var APIURL_STUDENT = DOMAIN_STUDENT
        var PG_RETURN_URL_STUDENT = BASE_URL_STUDENT + "api/Traknpay"
        var PG_RETURN_BULK_URL_STUDENT = BASE_URL_STUDENT + "api/Traknpay/balkFeeAdd"
        var PG_RETURN_TRANSPORT_BULK_URL_STUDENT = BASE_URL_STUDENT + "api/Traknpay/balkTransportFeeAdd"
        var BASEURL_WEBVIEW_STUDENT = BASE_URL_STUDENT + "site/Userlogin?username="
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
        val EXAM_GROUP_CLASS_BATCH_EXAM_ID = "exam_group_class_batch_exam_id"
        val RESULT_ID = "result_id"
        val DOWNLOAD_MARK_SHEET = "downloadMarksheet"
    }

    object StudentExamDetail {
        val TITLE = "title"
        val EXAM_ID = "exam_id"
    }

    object StudentHostel {
        val TITLE = "title"
        val HOSTELID = "hostelId"
    }

    object StudentLiveClass {
        val VIDEO_URL = "video_url"
    }
    object StudentDocument {
        val TITLE = "title"
        val FOLDER_ID = "folder_id"
    }

    object StudentOnlineExam {
        val EXAM_NAME = "name"
        val EXAMID = "examId"
        val DURATION = "duration"
        val TOTAL_QUESTION = "total_question"
        val DESCRIPTION = "description"
        val ONLINEEXAMNATIVE = "onlineExamNative"
        val WEBVIEWURL = "webviewUrl"
        val EXAM_FROM = "exam_from"
    }

    object StudentChat {
        val CHAT_NAME = "chatName"
        val CHAT_ID_UPDATE = "chat_id_update"
        val CHAT_ID_NEW_MESSAGE = "chat_id_new_message"
        val CHAT_CONNECTION_ID = "chat_connection_id"
        val GROUP_ID = "group_id"
    }

    object AppSaveData {
        var gallerystudenttype = "all"
        var listUpcoming : List<DatumLiveClass>? = null
        var listOnGoing : List<DatumLiveClass>? = null
        var listCompleted : List<DatumLiveClass>? = null
        var listGroupUsers : List<GroupUser?>? = null
    }

    object Preference {
        /*Student*/
        const val STUDENT_BASE_URL = "student_base_url"
        const val BACKGROUND_IMAGE = "background_image"
        const val LOGOUTSTATUS = "logoutStatus"
        const val LOGOUTSTATUS_VALUE = "manual"
        const val LANG_CODE_STUDENT = "lang_code_student"
        const val STUDENT_IS_LOGIN = "student_is_login"
        const val HAS_MULTIPLE_CHILD = "hasMultipleChild"
        const val HAS_MULTIPLE_CHILD_YES = "yes"
        const val HAS_MULTIPLE_CHILD_NO = "no"
        const val STUDENT_IS_LOGIN_YES = "yes"
        const val STUDENT_IS_LOGIN_NO = "no"
        const val APIURL_STUDENT = "apiUrl"
        const val IMAGESURL_STUDENT = "imagesUrl"
        const val STUDENT_ID = "studentId"
        const val SESSION_ID = "session_id"
        const val STUDENT_SESSION_ID = "student_session_id"
        const val CLASS_SECTION = "classSection"
        const val STUDENT_NAME = "studentName"
        const val CLASS_ID = "class_id"
        const val SECTION_ID = "section_id"
        const val ZOOM_SDK_KEY = "zoom_sdk_key"
        const val ZOOM_SDK_SECRAT = "zoom_sdk_secrat"

        /*Staff*/
        const val STAFF_BASE_URL = "staff_base_url"
        const val LANG_CODE_STAFF = "lang_code_staff"
        const val STAFF_IS_LOGIN = "staff_is_login"
        const val STAFF_IS_LOGIN_YES = "yes"
        const val BASE_URL_GET_OR_NOT = "base_url_get_or_not"
        const val BASE_URL_GET_OR_NOT_YES = "yes"
        const val EMAIL = "email"
        const val PASSWORD = "password"


        /*App Used*/
        const val APP_TYPE = "app_type"
        const val APP_TYPE_STAFF = "staff"
        const val APP_TYPE_STUDENT = "student"
        const val FETCH_BASE_URL = "fetch_base_url"
        const val SCHOOL_LOGO = "school_logo"
        const val SCHOOL_NAME = "school_name"
        const val BRANCH_ID = "branch_id"
        const val STAFF_LOGIN = "staff_login"
        const val STUDENT_LOGIN = "student_login"
        const val STUDENT_EMAIL = "email"
        const val STUDENT_PASSWORD = "password"


    }

    object HeaderParams{
        const val CLIENT_SERVICE = "Client-Service"
        const val CLIENT_SERVICE_VALUE = "school"
        const val AUTH_KEY = "Auth-Key"
        const val AUTH_KEY_VALUE = "schoolAdmin@"
        const val CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_VALUE = "application/json"
        const val NEW_KEY = "New-Key"
        const val USER_ID = "User-Id"
        const val DB_ID = "Db-Id"
    }

    object Params {
        const val WEB_CODE = "web_code"
    }

    object ParamsStaff {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val DEVICETOKEN = "deviceToken"
    }

    object ParamsStudent {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val DEVICETOKEN = "deviceToken"
        const val STUDENT_ID = "student_id"
        const val DATE_FROM = "Date_from"
        const val DATE_TO = "date_to"
        const val SUBJECT_ID = "subject_id"
        const val CLASSID = "classId"
        const val SECTIONID = "sectionId"
        const val MONTH = "month"
        const val YEAR = "year"
        const val STUDENT_SESSION_ID = "student_session_id"
        const val DATE = "date"
        const val TAG = "tag"
        const val SEARCH = "search"
        const val EXAMID = "examId"
        const val VEHICLEID = "vehicleId"
        const val HOSTELID = "hostelId"
        const val EXAM_ID = "exam_id"
        const val ONLINEEXAM_STUDENT_ID = "onlineexam_student_id"
        const val ONLINEEXAM_QUESTION_ID = "onlineexam_question_id"
        const val FILE = "files[]"
        const val IMG_ID = "img_id"
        const val TOTAL_ROWS = "total_rows"
        const val APPLY_DATE = "apply_date"
        const val FROM_DATE = "from_date"
        const val TO_DATE = "to_date"
        const val MESSAGE = "message"
        const val LEAVE_ID = "leave_id"
        const val USERFILE = "userfile"
        const val CLASS_ID = "class_id"
        const val SECTION_ID = "section_id"
        const val TYPE = "type"
        const val LIVE_CLASS_ID = "live_class_id"
        const val IN_TIME = "in_time"
        const val OUT_TIME = "out_time"
        const val FOLDER_ID = "folder_id"
        const val FIRST_DOC = "first_doc"
        const val EXAM_GROUP_CLASS_BATCH_EXAM_ID = "exam_group_class_batch_exam_id"
        const val RESULT_ID = "result_id"
        const val CHAT_CONNECTION_ID = "chat_connection_id"
        const val PAGENO = "pageno"
        const val CHAT_TO_USER = "chat_to_user"
        const val LAST_CHAT_ID = "last_chat_id"
        const val TIME = "time"
        const val GROUP_ID = "group_id"
        const val GROUP_MEMBER_ID = "group_member_id"
        const val USER_TYPE = "user_type"
        const val USER_ID = "user_id"
    }

    object Api {
        val GET_BRANCH_CODE = DOMAIN_STUDENT + "/webservice/getBranch"
    }

    object ApisStaff {
        val LOGIN = BASE_URL_STAFF + "login"
    }
    object ApisStudent {
        val LOGIN = DOMAIN_STUDENT + "/Webservice/login"
    }
}