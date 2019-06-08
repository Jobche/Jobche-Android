package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    lateinit var email: String

    lateinit var password: String

    private val size: Int = 20

    var page: Int = 0

    var tasks = ArrayList<Task>()

    val fabEventLiveData = SingleLiveData<Any>()

    val adapterEventLiveData = SingleLiveData<Any>()

    val updateAdapterEventLiveData = SingleLiveData<Any>()


    fun getAuthToken(): String {
        return Credentials.basic(email, password)
    }

    fun getCallAllTasks(): Call<Tasks> {
        return RetrofitClient().api.getTasks(getAuthToken(), page, size)
    }

    fun getCallMyTasks(): Call<Tasks> {
        return RetrofitClient().api.getMyTasks(getAuthToken(), page, size)
    }

    fun onClickFab() {
        fabEventLiveData.call()
    }

    fun generateTasks(call: Call<Tasks>) {
        call.enqueue(object : Callback<Tasks> {
            override fun onFailure(call: Call<Tasks>, t: Throwable) {
                Log.d("Get Tasks onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<Tasks>, response: Response<Tasks>) {
                Log.d("Get Tasks onSuccess:", response.body().toString())
                if (response.body() != null) {
                    if (response.body()!!.tasks.isNotEmpty()) {
                        tasks = (response.body()!!.tasks)
                        if (page == 0) {
                            adapterEventLiveData.call()
                        } else {
                            updateAdapterEventLiveData.call()
                        }

                    } else if (page == 0) {
                        Log.d("RecylerView", "It's Empty!")

                    } else {
                        Log.d("RecylerView", "No more tasks to show!")
                        page--
                    }
                }
            }
        })
    }
}