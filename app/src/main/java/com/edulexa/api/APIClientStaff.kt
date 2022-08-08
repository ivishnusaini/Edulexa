package com.edulexa.api

import android.app.Activity
import com.edulexa.support.Preference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClientStaff {
    companion object{

        fun getRetroFitClientWithHeader(context : Activity): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(240, TimeUnit.SECONDS)
            httpClient.connectTimeout(240, TimeUnit.SECONDS)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader(Constants.HeaderParams.CLIENT_SERVICE,Constants.HeaderParams.CLIENT_SERVICE_VALUE)
                    .addHeader(Constants.HeaderParams.AUTH_KEY,Constants.HeaderParams.AUTH_KEY_VALUE)
                    .addHeader(Constants.HeaderParams.CONTENT_TYPE,Constants.HeaderParams.CONTENT_TYPE_VALUE)
                    .addHeader(Constants.HeaderParams.DB_ID, Preference().getInstance(context)!!.getString(Constants.Preference.BRANCH_ID)!!)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            return Retrofit.Builder().baseUrl(Constants.BASE_URL_STAFF)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

        fun getRetroFitClientWithNewKeyHeader(context : Activity,accessToken : String,userId : String): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(240, TimeUnit.SECONDS)
            httpClient.connectTimeout(240, TimeUnit.SECONDS)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader(Constants.HeaderParams.CLIENT_SERVICE,Constants.HeaderParams.CLIENT_SERVICE_VALUE)
                    .addHeader(Constants.HeaderParams.AUTH_KEY,Constants.HeaderParams.AUTH_KEY_VALUE)
                    .addHeader(Constants.HeaderParams.CONTENT_TYPE,Constants.HeaderParams.CONTENT_TYPE_VALUE)
                    .addHeader(Constants.HeaderParams.NEW_KEY,accessToken)
                    .addHeader(Constants.HeaderParams.USER_ID,userId)
                    .addHeader(Constants.HeaderParams.DB_ID, Preference().getInstance(context)!!.getString(Constants.Preference.BRANCH_ID)!!)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            return Retrofit.Builder().baseUrl(Constants.BASE_URL_STAFF)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

        fun getRetroFitClient(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(240, TimeUnit.SECONDS)
            httpClient.connectTimeout(240, TimeUnit.SECONDS)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            return Retrofit.Builder().baseUrl(Constants.BASE_URL_STAFF)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

    }
}