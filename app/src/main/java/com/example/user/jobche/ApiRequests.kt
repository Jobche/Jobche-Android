package com.example.user.jobche

import com.example.user.jobche.Model.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET



interface ApiRequests {
    @Headers("Content-Type: application/json")

    @POST("users")
    fun createUser(@Body body:JsonObject): Call<User>

    @POST("users/login")
    fun loginUser(@Body body:JsonObject): Call<User>

    @POST("tasks")
    fun createTask(@Header("Authorization") auth:String ,@Body body:JsonObject): Call<Task>

    @GET("tasks")
    fun getTasks(
        @Header("Authorization") auth:String,
        @Query("page") page: Int,
        @Query("size") size: Int): Call<Tasks>

    @POST("application")
        fun applyForTask(@Header("Authorization") auth: String, @Body taskId:JsonObject): Call<Application>

    @GET("users/{id}")
        fun getUser(@Header("Authorization") auth: String, @Path("id") id:Int): Call<UserProfile>
}