package com.edulexa.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterfaceStudent {
    @POST("Webservice/login")
    fun studentLogin(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/Dashboard")
    fun getDashboardData(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getHomework")
    fun getHomeworkData(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getSubjectList")
    fun getSubjectList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getAttendenceRecords")
    fun getAttendance(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getNotifications")
    fun getNoticeboardData(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/fees")
    fun getFeesData(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getCustomLession")
    fun getCustomLessonPlan(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getPublicEvents")
    fun getCalendarList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/class_schedule")
    fun getClassSchedule(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getDownloadsLinks")
    fun getDownloadLinks(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/examSchedule")
    fun getExamSchedule(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getExamSchedule")
    fun getExamScheduleDetail(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getLibraryBookIssued")
    fun getLibraryBookIssued(@Body requestBody : RequestBody): Call<ResponseBody>

    @GET("Webservice/getLibraryBooks")
    fun getLibraryAllBooks(): Call<ResponseBody>

    @POST("Webservice/getTransportroute")
    fun getTransportRoute(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getTransportVehicleDetails")
    fun getTransportVehicleDetails(@Body requestBody : RequestBody): Call<ResponseBody>

    @GET("Webservice/getHostelList")
    fun getHostelList(): Call<ResponseBody>

    @POST("Webservice/getHostelDetails")
    fun getHostelDetails(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getOnlineExam")
    fun getOnlineExamList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/startOnlineExam")
    fun getOnlineExamQuestionAnsList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/submitSubjectiveQuestion")
    fun submitSubjectiveQuestion(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/deleteSubjectiveimages")
    fun deleteSubjectiveimages(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/submitOnlineExam")
    fun submitOnlineExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/getLeaveList")
    fun getLeaveList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/addLeave")
    fun addLeave(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/logout")
    fun logout(): Call<ResponseBody>

    @POST("Webservice/getyoutube_live")
    fun getLiveClasses(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/updateLiveClassStudent")
    fun updateLiveClassStudent(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getTeachersList")
    fun getTeachersList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getDocument")
    fun getDocument(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getFolderData")
    fun getFolderData(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/create_doc")
    fun uploadDocument(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getExamResultList")
    fun getReportCardList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getExamResultDetails")
    fun getExamResultDetails(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getAlbumList")
    fun getAlbumList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/myuserlist")
    fun getChatUserList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/getChatRecord")
    fun getChatRecord(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/chatUpdate")
    fun getChatUpdate(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("Webservice/newMessage")
    fun sendNewMessage(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/getGroupChatRecord")
    fun getGroupChatRecord(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/groupChatUpdate")
    fun getGroupChatUpdate(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/newGroupMessage")
    fun sendNewGroupMessage(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/searchNewUser")
    fun getStaffListForChat(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("webservice/addNewChatUser")
    fun addNewChatUser(@Body requestBody : RequestBody): Call<ResponseBody>
}