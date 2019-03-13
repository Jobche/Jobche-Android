package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.user.jobche.Model.UserProfile
import com.example.user.jobche.Model.Work
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsViewModel : ViewModel(){

    lateinit var email: String
    lateinit var password: String
    var workId: Long = 0
    var workers = ArrayList<UserProfile>()
    private val _adapterEventLiveData = SingleLiveData<Any>()

    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

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
                    _adapterEventLiveData.call()
                }
            }
        })
    }

}