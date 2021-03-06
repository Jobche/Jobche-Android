package com.example.user.jobche

import com.example.user.jobche.Model.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET


interface ApiRequests {
    @Headers("Content-Type: application/json")

    @POST("users")
    fun createUser(@Body registerUser: JsonObject): Call<User>

    @POST("users/login")
    fun loginUser(@Body body: JsonObject): Call<User>

    @GET("users/me")
    fun checkUser(@Header("Authorization") auth: String): Call<UserProfile>

    @POST("tasks")
    fun createTask(@Header("Authorization") auth: String, @Body body: JsonObject): Call<Task>

    @GET("tasks")
    fun getTasks(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Tasks>

    @GET("tasks")
    fun getFilteredTasks(
        @Header("Authorization") auth: String,
        @Query("title") title: String?,
        @Query("city") city: String?,
        @Query("dateStart") dateStart: DateTime?,
        @Query("dateEnd") dateEnd: DateTime?,
        @Query("numWStart") numWStart: Int?,
        @Query("pStart") pStart: Int?,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Tasks>

    @GET("tasks/me")
    fun getMyTasks(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Tasks>

    @GET("tasks/{id}")
    fun getTask(@Header("Authorization") auth: String, @Path("id") id: Long): Call<Task>

    @POST("application")
    fun applyForTask(@Header("Authorization") auth: String, @Body taskId: JsonObject): Call<Application>

    @GET("users/{id}")
    fun getUser(@Header("Authorization") auth: String, @Path("id") id: Long): Call<UserProfile>

    @GET("users/me/applications")
    fun getMyApplications(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Applications>

    @GET("tasks/{taskId}/applications")
    fun getAppliers(
        @Header("Authorization") auth: String,
        @Path("taskId") taskId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<Applications>

    @POST("application/approve/{id}")
    fun acceptApplier(
        @Header("Authorization") auth: String,
        @Path("id") id: Long
    ): Call<Application>

    @POST("work")
    fun startWork(@Header("Authorization") auth: String, @Body body: JsonObject): Call<Work>

    @GET("work/{id}")
    fun getWork(@Header("Authorization") auth: String, @Path("id") workId: Long): Call<Work>

    @PATCH("work/{id}")
    fun endWork(@Header("Authorization") auth: String, @Path("id") id: Long,  @Body body: Status): Call<Work>

    @POST("reviews")
    fun reviewUser(@Header("Authorization") auth: String, @Body body: JsonObject): Call<Review>

    @Multipart
    @POST("picture/profile")
    fun uploadImage(@Header("Authorization") auth: String, @Part file: MultipartBody.Part): Call<ResponseBody>

    @GET("picture/profile")
    fun getImage(@Header("Authorization") auth: String): Call<ResponseBody>
}
