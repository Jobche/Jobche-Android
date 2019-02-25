package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilteredTasksViewModel : ViewModel() {
    private lateinit var email:String

    private lateinit var password:String

    private var page: Int = 0

    private val size: Int = 20

    var tasks =  ArrayList<Task>()

    private val _adapterEventLiveData = SingleLiveData<Any>()

    private val _updateAdapterEventLiveData = SingleLiveData<Any>()

    fun getPage(): Int {
        return this.page
    }

    fun setPage(page: Int) {
        this.page = page
    }


    fun getSize(): Int {
        return this.size
    }

    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
    }


    fun getPassword(): String {
        return this.password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    val adapterEventLiveData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData


    fun getAuthToken(): String {
        return Credentials.basic(getEmail(), getPassword())
    }

    fun filterTasks(filter: Filter) {

        val call: Call<Tasks> = RetrofitClient().getApi()
            .getFilteredTasks(
                getAuthToken(), filter.title, filter.city, filter.dateStart, filter.dateEnd,
                filter.numWStart?.toIntOrNull() , filter.pStart?.toIntOrNull(), getPage(), getSize()
            )

        call.enqueue(object : Callback<Tasks> {
            override fun onFailure(call: Call<Tasks>, t: Throwable) {
                Log.d("Search Task onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<Tasks>, response: Response<Tasks>) {
                Log.d("Search Task onSuccess:", response.body().toString())
                if (response.body() != null) {
                    tasks = (response.body()!!.tasks)
                    if (getPage() == 0) {
                        _adapterEventLiveData.call()
                    } else {
                        _updateAdapterEventLiveData.call()
                    }
                } else if (getPage() == 0) {
                    Log.d("RecylerView", "It's Empty!")

                } else {
                    Log.d("RecylerView", "No more tasks to show!")
                    setPage(getPage() - 1)
                }
            }

        })

    }
}
