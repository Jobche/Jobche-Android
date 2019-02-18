package com.example.user.jobche

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Response


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


    fun getPassword(): String {
        return this.password
    }

    fun getApi() : ApiRequests {
        return getClient()!!.create(ApiRequests::class.java)
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

    fun getAuthClient() : Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit
    }
}

class BasicAuthInterceptor(val email: String, val password: String): Interceptor {

    private val credentials: String
        get() = Credentials.basic(email, password)


    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}