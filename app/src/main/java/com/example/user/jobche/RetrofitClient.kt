package com.example.user.jobche

import com.google.gson.GsonBuilder
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

// set your desired log level

    val httpClient:OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logging)
//    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
//    httpClient.addInterceptor(logging);  // <-- this is the important line!


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