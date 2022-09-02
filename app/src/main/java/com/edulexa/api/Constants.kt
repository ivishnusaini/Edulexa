package com.edulexa.api

import com.edulexa.activity.staff.dashboard.model.notifications.DatumNotification
import com.edulexa.activity.staff.homework.model.homeworklist.Homework
import com.edulexa.activity.student.chat.model.group.GroupUser
import com.edulexa.activity.student.live_classes.model.DatumLiveClass

class Constants {
    companion object {
//        val BASE_URL_SCHOOL_CODE = "https://edulexa.online/beta/staff_api/app/web_url"
        val BASE_URL_SCHOOL_CODE = "https://demo.edulexa.online/staff_api/app/web_url"

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
        val MODULE_ID_VALUE = "13"
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

    object StaffStudentProfile{
        val CLASS_ID = "class_id"
        val SECTION_ID = "section_id"
        val TOKEN = "token"
        val STUDENT_ID = "student_id"
        val STUDENT_SESSION_ID = "student_session_id"
        val IMAGE = "image"
        val NAME = "name"
        val ROLL_NO = "roll_no"
        val CLASS = "class"
        val SECTION = "section"
        val FROM_WHERE = "from_where"
    }

    object StaffK12Timeline{
        val STUDENT_ID = "student_id"
    }
    object StaffNotification{
        val FROM_WHERE = "from_where"
        val DATUM_NOTIFICATION = "datum_notification"
    }

    object StaffCustomLessonPlan{
        val CLASS_ID = "class_id"
        val SECTION_ID = "section_id"
        val SECTION_NAME = "section_name"
        val SUBJECT_ID = "subject_id"
        val ID = "id"
        val FORM_FIELD = "form_field"
    }

    object StaffOnlineExam {
        val ONLINE_EXAM_ID = "online_exam_id"
        val EXAM_MODEL = "exam_model"
        val EXAM_TYPE = "exam_type"
        val EXAM_ID = "exam_id"
        val EXAM_NAME = "exam_name"
        val TYPE = "type"
        val QUESTION_ID = "question_id"
        val ONLINEEXAM_STUDENT_ID = "onlineexam_student_id"
        val IMAGES = "images"
        val IMAGEURL = "imageUrl"
    }

    object StaffHomework {
        val HOMEWORK_ID = "homework_id"
        val IMAGES = "images"
        val IMAGEURL = "imageUrl"
    }

    object StaffLessonList {
        val STAFF_ID = "staff_id"
        val CLASS_ID = "class_id"
        val SECTION_ID = "section_id"
        val SUBJECT_GROUP_ID = "subject_group_id"
        val SUBJECT_GROUP_SUBJECT_ID = "subject_group_subject_id"
        val TIME_FROM = "time_from"
        val TIME_TO = "time_to"
        val DATE = "date"
        val ROLE_ID = "role_id"
        val SYLLABUS_ID = "syllabus_id"
        val TYPE = "type"
    }

    object AppSaveData {
        var gallerystudenttype = "all"
        var listUpcoming : List<DatumLiveClass>? = null
        var listOnGoing : List<DatumLiveClass>? = null
        var listCompleted : List<DatumLiveClass>? = null
        var listGroupUsers : List<GroupUser?>? = null

        /*Staff*/
        var staffNotificationList : List<DatumNotification?>? = null
        var homeworkList : List<Homework?>? = null
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
        const val USERNAME_STUDENT = "username"
        const val PASSWORD_STUDENT = "password"
        const val DEVICETOKEN = "deviceToken"

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
        const val STAFF_ID = "staff_id"
        const val MODULE_ID = "module_id"
        const val CLASS_ID = "class_id"
        const val SECTION_ID = "section_id"
        const val STUDENT_ID = "student_id"
        const val ROLE_ID = "role_id"
        const val TIMELINE_ID = "timeline_id"
        const val VISIBLE_CHECK = "visible_check"
        const val TIMELINE_DATE = "timeline_date"
        const val TIMELINE_TITLE = "timeline_title"
        const val TIMELINE_DESC = "timeline_desc"
        const val TIMELINE_DOC = "timeline_doc"
        const val DATE = "date"
        const val SUBJECT_ID = "subject_id"
        const val CLASS_SECTION_ID = "class_section_id"
        const val SHOW_ON_DATE = "show_on_date"
        const val CONTENT_TITLE = "content_title"
        const val LESSON = "lesson"
        const val TOPIC = "topic"
        const val GENERAL_OBJECTIVES = "general_objectives"
        const val TEACHING_METHOD = "teaching_method"
        const val PREVIOUS_KNOWLEDGE = "previous_knowledge"
        const val COMPREHENSIVE_QUESTIONS = "comprehensive_questions"
        const val LECTURE_YOUTUBE_URL = "lecture_youtube_url"
        const val PERIOD = "period"
        const val TEACHING_AIDS = "teaching_aids"
        const val PORTION_ACTUALLY_TAUGHT = "portion_actually_taught"
        const val HW_ASSIGNED = "hw_assigned"
        const val HW_NOT_ASSIGNED_REASON = "hw_not_assigned_reason"
        const val NOTE = "note"
        const val UPLOAD_DATE = "upload_date"
        const val FILE = "file"
        const val LESSON_ID = "lesson_id"
        const val EXAM_ID = "EXAM_ID"
        const val ONLINEEXAM_ID = "onlineexam_id"
        const val EXAM = "exam"
        const val IS_ACTIVE = "is_active"
        const val PUBLISH_RESULT = "publish_result"
        const val DURATION = "duration"
        const val DESCRIPTION = "description"
        const val EXAM_FROM = "exam_from"
        const val EXAM_TO = "exam_to"
        const val PASSING_PERCENTAGE = "passing_percentage"
        const val PASSING_MARK_TYPE = "passing_mark_type"
        const val ONLINE_EXAM_ID = "online_exam_id"
        const val QUESTION_ID = "question_id"
        const val QUESTION_TYPE_ID = "question_type_id"
        const val CORRECT = "correct"
        const val QUESTION = "question"
        const val QUESTION_MARK = "question_mark"
        const val QUESTION_NMARK = "question_nmark"
        const val OPT_A = "opt_a"
        const val OPT_B = "opt_b"
        const val OPT_C = "opt_c"
        const val OPT_D = "opt_d"
        const val OPT_E = "opt_e"
        const val ATTACH_FILE = "attach_file"
        const val ID = "id"
        const val IMG_URL = "img_url"
        const val ONLINEEXAM_STUDENT_ID = "onlineexam_student_id"
        const val HOMEWORK_ID = "homework_id"
        const val STUDENT_LIST = "student_list"
        const val COMMENT = "comment"
        const val RESUBMIT = "resubmit"
        const val STUDENT_SESSION_ID = "student_session_id"
        const val SUBJECT_GROUP_ID = "subject_group_id"
        const val HOMEWORK_DATE = "homework_date"
        const val SUBMIT_DATE = "submit_date"
        const val USERFILE = "userfile[]"
        const val SYLLABUS_ID = "syllabus_id"
        const val SUBJECT_GROUP_SUBJECT_ID = "subject_group_subject_id"
        const val SUBJECT_GROUP_CLASS_SECTIONS_ID = "subject_group_class_sections_id"
        const val CREATED_FOR = "created_for"
        const val TOPIC_ID = "topic_id"
        const val TIME_FROM = "time_from"
        const val TIME_TO = "time_to"
        const val PRESENTATION = "presentation"
        const val SUB_TOPIC = "sub_topic"
        const val NOTIFICATION_ID = "notification_id"
    }

    object ParamsStudent {
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