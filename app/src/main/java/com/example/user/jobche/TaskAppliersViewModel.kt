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

class TaskAppliersViewModel {

    private var page: Int = 0

    private val size: Int = 20

    private lateinit var email: String

    private lateinit var password: String

    private var taskId: Int = 0

    private var appliers = ArrayList<UserProfile>()

    private var applications = ArrayList<Application>()

    private val _adapterEventLiveData = SingleLiveData<Any>()

    private val _updateAdapterEventLiveData = SingleLiveData<Any>()


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

    fun getPage(): Int {
        return this.page
    }

    fun setPage(page: Int) {
        this.page = page
    }

    fun getSize(): Int {
        return this.size
    }

    fun getAuthToken(): String {
        return Credentials.basic(getEmail(), getPassword())
    }

    fun getTaskId(): Int {
        return this.taskId
    }

    fun setTaskId(taskId: Int) {
        this.taskId = taskId
    }


    fun getAppliers() : ArrayList<UserProfile> {
        return this.appliers
    }

    fun getApplications(): ArrayList<Application> {
        return this.applications
    }

    fun setApplications(applications: ArrayList<Application> ) {
        this.applications = applications
    }


    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData


    fun getTaskAppliers() {
        val call = RetrofitClient().getApi()
            .getAppliers(getAuthToken(), getTaskId(), getPage(), getSize())

        call.enqueue(object : Callback<Applications> {
            override fun onFailure(call: retrofit2.Call<Applications>, t: Throwable) {
                Log.d("My Apply onFailure ", t.message.toString())
            }

            override fun onResponse(call: retrofit2.Call<Applications>, response: Response<Applications>) {
                Log.d("My Apply onSuccess", response.body().toString())
                if (response.body() != null) {
                    setApplications(response.body()!!.applications)
                    for (appl in getApplications()) {
                        getAppliers().add(appl.applicant)
                    }
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

