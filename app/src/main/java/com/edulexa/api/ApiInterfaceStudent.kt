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
}