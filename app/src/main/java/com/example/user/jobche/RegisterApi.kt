package com.example.user.jobche

import com.example.user.jobche.Model.RegisterUser
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface RegisterApi {
    @Headers("Content-Type: application/json")
    @POST("users")
    fun createUser(@Body body:JsonObject): Call<RegisterUser>

}