package com.example.user.jobche

import com.example.user.jobche.Model.LoginUser
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.Model.Task
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface RegisterApi {
    @Headers("Content-Type: application/json")

    @POST("users")
    fun createUser(@Body body:JsonObject): Call<RegisterUser>

    @POST("users/login")
    fun loginUser(@Body body:JsonObject): Call<LoginUser>

    @POST("tasks")
    fun createTask(@Header("Authorization") auth:String ,@Body body:JsonObject): Call<ResponseBody>
}