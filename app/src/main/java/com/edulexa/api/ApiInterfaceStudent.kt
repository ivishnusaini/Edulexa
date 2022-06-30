package com.edulexa.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
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
}