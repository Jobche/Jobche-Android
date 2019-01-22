package com.example.user.jobche

import com.example.user.jobche.Model.*
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET



interface RegisterApi {
    @Headers("Content-Type: application/json")

    @POST("users")
    fun createUser(@Body body:JsonObject): Call<RegisterUser>

    @POST("users/login")
    fun loginUser(@Body body:JsonObject): Call<LoginUser>

    @POST("tasks")
    fun createTask(@Header("Authorization") auth:String ,@Body body:JsonObject): Call<ResponseBody>

    @GET("tasks")
    fun getTasks(
        @Header("Authorization") auth:String,
        @Query("page") page: Int,
        @Query("size") size: Int): Call<Tasks>

    @GET("tasks/1")
    fun getTask(
        @Header("Authorization") auth:String
    ): Call<Tasks>
}