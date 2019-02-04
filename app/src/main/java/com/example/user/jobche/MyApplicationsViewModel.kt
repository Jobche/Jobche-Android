package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel;
import android.support.annotation.MainThread
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.Applications
import com.example.user.jobche.Model.Task
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyApplicationsViewModel : ViewModel() {

    private var page: Int = 0

    private val size: Int = 20

    private lateinit var email: String

    private lateinit var password: String

    private val ids = ArrayList<Int>()

    private val creatorIds = ArrayList<Int>()

    private val tasks = ArrayList<Task>()

    private val _adapterEventLiveData = SingleLiveData<Any>()

    private val _updateAdapterEventLiveData = SingleLiveData<Any>()


    fun getPage(): Int {
        return this.page
    }

    fun getSize(): Int {
        return this.size
    }

    fun setPage(page: Int) {
        this.page = page
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


    fun getAuthToken(): String {
        return Credentials.basic(getEmail(), getPassword())
    }

    fun getCreatorIds(): ArrayList<Int> {
        return this.creatorIds
    }

    fun getIds(): ArrayList<Int> {
        return this.ids
    }

    fun getTasks(): ArrayList<Task> {
        return this.tasks
    }

    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData


    fun getAppliedTasks() {
        val call: Call<Applications> = RetrofitClient().getApi()
            .getMyApplications(getAuthToken(), getPage(), getSize())

        call.enqueue(object : Callback<Applications> {
            override fun onFailure(call: Call<Applications>, t: Throwable) {
                Log.d("My Apply onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<Applications>, response: Response<Applications>) {
                Log.d("Get User onSuccess", response.body().toString())
                if (response.body() != null) {
                    for (appl: Application in response.body()!!.applications) {
                        addTask(appl.taskId)

                    }
                    if (getPage() == 0) {
                        _adapterEventLiveData.call()
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

    fun addTask(taskId: Int) {

        val call: Call<Task> = RetrofitClient().getApi()
            .getTask(getAuthToken(), taskId)

        call.enqueue(object : Callback<Task> {
            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d("Apply Task onFailure ", t.message.toString())

            }

            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                Log.d("Apply Task onSuccess", response.body().toString())
                if (response.body() != null) {
                    getTasks().add(response.body()!!)
                    _updateAdapterEventLiveData.call()
                }
            }
        })
    }
}
