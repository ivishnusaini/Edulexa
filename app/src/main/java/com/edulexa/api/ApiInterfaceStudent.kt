package com.edulexa.api

import com.edulexa.activity.student.login.request_model.RequestParamModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterfaceStudent {
    @POST("Webservice/login")
    fun studentLogin(@Body requestBody : RequestBody): Call<ResponseBody>
}