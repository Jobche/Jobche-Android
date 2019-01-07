package com.example.user.jobche

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

const val BASE_URL: String = "http://84.238.140.141:8080/"


class RetrofitClient {

    var retrofit: Retrofit? = null

    val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(Level.BODY)

    val httpClient:OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logging)

    fun getApi() : RegisterApi {
        return getClient()!!.create(RegisterApi::class.java)
    }

    fun getClient() : Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }
        return retrofit
    }
}