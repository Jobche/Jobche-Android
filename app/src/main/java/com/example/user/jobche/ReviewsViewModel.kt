package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.user.jobche.Model.Review
import com.example.user.jobche.Model.ReviewGrade
import com.example.user.jobche.Model.UserProfile
import com.example.user.jobche.Model.Work
import com.google.gson.JsonObject
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsViewModel : ViewModel(){

    lateinit var email: String
    lateinit var password: String
    var workId: Long = 0
    var workers = ArrayList<UserProfile>()
    val adapterEventLiveData = SingleLiveData<Any>()

    fun getWork() {
        val call: Call<Work> = RetrofitClient().api
            .getWork(Credentials.basic(email, password), workId)

        call.enqueue(object : Callback<Work> {
            override fun onFailure(call: Call<Work>, t: Throwable) {
                Log.d("Get work onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<Work>, response: Response<Work>) {
                Log.d("Get work onSuccess", response.body().toString())
                if (response.body() != null) {
                    workers = response.body()!!.workers
                    adapterEventLiveData.call()
                }
            }
        })
    }

    fun reviewUser(ratingStars: Int, comment: String, userId: Long, workId: Long) {

        val paramObject = JsonObject()
        paramObject.addProperty("comment", comment)
        paramObject.addProperty("reviewGrade", ReviewGrade.values()[ratingStars - 1].toString())
        paramObject.addProperty("userId", userId)
        paramObject.addProperty("workId", workId)

        val call: Call<Review> = RetrofitClient().api
            .reviewUser(Credentials.basic(email, password), paramObject)

        call.enqueue(object : Callback<Review> {
            override fun onFailure(call: Call<Review>, t: Throwable) {
                Log.d("Post review onFailure ", t.message.toString())

            }

            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                Log.d("Post review onSuccess", response.body().toString())

            }
        })
    }
}