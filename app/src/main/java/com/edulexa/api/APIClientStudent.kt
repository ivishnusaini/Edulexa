package com.edulexa.api

import android.app.Activity
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClientStudent {
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
            var baseUrl = ""
            if (Constants.DOMAIN_STUDENT.endsWith("/"))
                baseUrl = Constants.DOMAIN_STUDENT
            else baseUrl = Constants.DOMAIN_STUDENT + "/"
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

        fun getRetroFitClientWithNewKeyHeader(context : Activity,accessToken : String,branchID : String,userId : String): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(240, TimeUnit.SECONDS)
            httpClient.connectTimeout(240, TimeUnit.SECONDS)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader(Constants.HeaderParams.CLIENT_SERVICE,Constants.HeaderParams.CLIENT_SERVICE_VALUE)
                    .addHeader(Constants.HeaderParams.AUTH_KEY,Constants.HeaderParams.AUTH_KEY_VALUE)
                    .addHeader(Constants.HeaderParams.CONTENT_TYPE,Constants.HeaderParams.CONTENT_TYPE_VALUE)
                    .addHeader(Constants.HeaderParams.DB_ID, branchID)
                    .addHeader(Constants.HeaderParams.NEW_KEY, accessToken)
                    .addHeader(Constants.HeaderParams.USER_ID, userId)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            var baseUrl = ""
            if (Constants.DOMAIN_STUDENT.endsWith("/"))
                baseUrl = Constants.DOMAIN_STUDENT
            else baseUrl = Constants.DOMAIN_STUDENT + "/"
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

        fun getRetroFitClientWithoutDbId(context : Activity,accessToken : String,userId : String): Retrofit {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(240, TimeUnit.SECONDS)
            httpClient.connectTimeout(240, TimeUnit.SECONDS)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .addHeader(Constants.HeaderParams.CLIENT_SERVICE,Constants.HeaderParams.CLIENT_SERVICE_VALUE)
                    .addHeader(Constants.HeaderParams.AUTH_KEY,Constants.HeaderParams.AUTH_KEY_VALUE)
                    .addHeader(Constants.HeaderParams.CONTENT_TYPE,Constants.HeaderParams.CONTENT_TYPE_VALUE)
                    .addHeader(Constants.HeaderParams.NEW_KEY, accessToken)
                    .addHeader(Constants.HeaderParams.USER_ID, userId)
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            var baseUrl = ""
            if (Constants.DOMAIN_STUDENT.endsWith("/"))
                baseUrl = Constants.DOMAIN_STUDENT
            else baseUrl = Constants.DOMAIN_STUDENT + "/"
            return Retrofit.Builder().baseUrl(baseUrl)
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
            var baseUrl = ""
            if (Constants.DOMAIN_STUDENT.endsWith("/"))
                baseUrl = Constants.DOMAIN_STUDENT
            else baseUrl = Constants.DOMAIN_STUDENT + "/"
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

    }
}