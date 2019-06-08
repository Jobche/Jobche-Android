package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilteredTasksViewModel : ViewModel() {
    lateinit var email:String

    lateinit var password:String

    var page: Int = 0

    private val size: Int = 20

    var tasks =  ArrayList<Task>()

     val adapterEventLiveData = SingleLiveData<Any>()

    val updateAdapterEventLiveData = SingleLiveData<Any>()

//    val adapterEventLiveData: LiveData<Any>
//        get() = _adapterEventLiveData
//
//    val updateAdapterEventLiveData: LiveData<Any>
//        get() = _updateAdapterEventLiveData


    fun getAuthToken(): String {
        return Credentials.basic(email, password)
    }

    fun filterTasks(filter: Filter) {

        val call: Call<Tasks> = RetrofitClient().api
            .getFilteredTasks(
                getAuthToken(), filter.title, filter.city, filter.dateStart, filter.dateEnd,
                filter.numWStart?.toIntOrNull() , filter.pStart?.toIntOrNull(), page, size
            )

        call.enqueue(object : Callback<Tasks> {
            override fun onFailure(call: Call<Tasks>, t: Throwable) {
                Log.d("Search Task onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<Tasks>, response: Response<Tasks>) {
                Log.d("Search Task onSuccess:", response.body().toString())
                if (response.body() != null) {
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

        })

    }
}
