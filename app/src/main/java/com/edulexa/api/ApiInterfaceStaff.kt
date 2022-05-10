package com.edulexa.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface ApiInterfaceStaff {
    @Multipart
    @POST("login")
    fun staffLogin(@Part(Constants.ParamsStaff.USERNAME) userName: RequestBody?,
                    @Part(Constants.ParamsStaff.PASSWORD) password: RequestBody?,
                    @Part(Constants.ParamsStaff.DEVICETOKEN) deviceToken: RequestBody?): Call<ResponseBody>
}