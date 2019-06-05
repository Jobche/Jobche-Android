package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel;
import android.util.Log
import com.example.user.jobche.Model.Applications
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyApplicationsViewModel : ViewModel() {

    var page: Int = 0

    private val size: Int = 20

    lateinit var email: String

    lateinit var password: String

    val appliedTasks = ArrayList<Task>()

    val acceptedTasks = ArrayList<Task>()

    private val _adapterEventLiveData = SingleLiveData<Any>()

    private val _updateAdapterEventLiveData = SingleLiveData<Any>()

    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData


    fun getAppliedTasks() {
        val call: Call<Applications> = RetrofitClient().api
            .getMyApplications(Credentials.basic(email, password), page, size)

        call.enqueue(object : Callback<Applications> {
            override fun onFailure(call: Call<Applications>, t: Throwable) {
                Log.d("My Apply onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<Applications>, response: Response<Applications>) {
                Log.d("My Apply onSuccess", response.body().toString())
                if (response.body() != null) {
                    for (appl in response.body()!!.applications) {
                        if (appl.accepted) {
                            acceptedTasks.add(appl.task)
                        } else {
                            appliedTasks.add(appl.task)
                        }
                    }
                    if (page == 0) {
                        _adapterEventLiveData.call()
                    } else {
                        _updateAdapterEventLiveData.call()
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
