package com.edulexa.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterfaceStaff {
    @Multipart
    @POST("login")
    fun staffLogin(@Part(Constants.ParamsStaff.USERNAME) userName: RequestBody?,
                    @Part(Constants.ParamsStaff.PASSWORD) password: RequestBody?,
                    @Part(Constants.ParamsStaff.DEVICETOKEN) deviceToken: RequestBody?): Call<ResponseBody>


    @Multipart
    @POST("getClasses")
    fun getClasses(@Part(Constants.ParamsStaff.STAFF_ID) staffId: RequestBody?): Call<ResponseBody>










    @POST("getNotifications")
    fun getNotifications(@Body requestBody : RequestBody): Call<ResponseBody>

    /*@POST("getClasses")
    fun getClasses(@Body requestBody : RequestBody): Call<ResponseBody>*/

    @POST("getClassSections")
    fun getClassSections(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getStudentsByClass")
    fun getStudentsByClass(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("view_student_profile")
    fun getStudentProfile(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("view_student_timeline")
    fun getK12Timeline(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("delete_student_timeline")
    fun deleteStudentTimeline(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("add_student_timeline")
    fun addStudentTimeline(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getcustomlessonplan")
    fun getcustomlessonplan(@Body requestBody : RequestBody): Call<ResponseBody>
}