package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.telecom.Call
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.Applications
import com.example.user.jobche.Model.UserProfile
import okhttp3.Credentials
import retrofit2.Callback
import retrofit2.Response

class TaskAcceptedViewModel(val task: Task, private val email: String, private val password: String) {

    var page: Int = 0

    val size: Int = 20

    var appliers = ArrayList<UserProfile>()

    var applications = ArrayList<Application>()

    private val _adapterEventLiveData = SingleLiveData<Any>()

    private val _updateAdapterEventLiveData = SingleLiveData<Any>()

    private val _onClickEventLiveData = SingleLiveData<Any>()


    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData


    val onClickEventLiveData: LiveData<Any>
        get() = _onClickEventLiveData


    fun onTaskClick() {
        _onClickEventLiveData.call()
    }

    fun getTaskAppliers() {
        val call = RetrofitClient().getApi()
            .getAppliers(Credentials.basic(email, password), task.id, page, size)

        call.enqueue(object : Callback<Applications> {
            override fun onFailure(call: retrofit2.Call<Applications>, t: Throwable) {
                Log.d("My Apply onFailure ", t.message.toString())
            }

            override fun onResponse(call: retrofit2.Call<Applications>, response: Response<Applications>) {
                Log.d("My Apply onSuccess", response.body().toString())
                if (response.body() != null) {
                    applications = response.body()!!.applications
                    for (appl in applications) {
                        appliers.add(appl.applicant)
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

