package com.example.user.jobche

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import okhttp3.OkHttpClient


const val BASE_URL: String = "https://api.jobche.net"

class RetrofitClient {

    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(Level.BODY)

    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logging)

    val api: ApiRequests
        get() = client.create(ApiRequests::class.java)

    private val client: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

}