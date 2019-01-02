package com.example.user.jobche

import com.example.user.jobche.Model.RegisterUser
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @POST("users")
    @FormUrlEncoded
    fun createUser(@Field("email") email: String,
                    @Field("firstName") firstName: String,
                    @Field("lastName") lastName: String,
                    @Field("password")password: String): Call<RegisterUser>
}