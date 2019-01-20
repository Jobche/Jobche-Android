package com.example.user.jobche

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import okhttp3.OkHttpClient



const val BASE_URL: String = "http://84.238.140.141:8080/"


class RetrofitClient {

    private var email: String = ""

    private var password: String = ""

    private var retrofit: Retrofit? = null

    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(Level.BODY)

    private val httpClient:OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logging)

    private var client = OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor(getEmail(), getPassword()))
        .build()

    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getPassword(): String {
        return this.password
    }

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