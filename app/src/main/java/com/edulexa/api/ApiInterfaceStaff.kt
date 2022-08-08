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

    @POST("getClasses")
    fun getClasses(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getClassSections")
    fun getClassSections(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getStudentsByClass")
    fun getStudentsByClass(@Body requestBody : RequestBody): Call<ResponseBody>
}