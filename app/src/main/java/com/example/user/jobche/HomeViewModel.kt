package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.user.jobche.Model.Task
import com.example.user.jobche.Model.Tasks
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel {

    private lateinit var email: String

    private lateinit var password: String

    private val size: Int = 20

    private var page: Int = 0

    private lateinit var tasks: ArrayList<Task>

    private val _fabEventLiveData = SingleLiveData<Any>()

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

    fun getCallAllTasks(): Call<Tasks> {
        return RetrofitClient().getApi().getTasks(getAuthToken(), getPage(), getSize())
    }

    fun getCallMyTasks(): Call<Tasks> {
        return RetrofitClient().getApi().getMyTasks(getAuthToken(), getPage(), getSize())
    }

    fun getTasks(): ArrayList<Task> {
        return this.tasks
    }

    fun setTasks(tasks: ArrayList<Task>) {
        this.tasks = tasks
    }

    val fabEventLiveData: LiveData<Any>
        get() = _fabEventLiveData

    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData

    fun onClickFab() {
        _fabEventLiveData.call()
    }


    fun generateTasks(call: Call<Tasks>) {
        call.enqueue(object : Callback<Tasks> {
            override fun onFailure(call: Call<Tasks>, t: Throwable) {
                Log.d("Add Task onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<Tasks>, response: Response<Tasks>) {
                Log.d("Add Task onSuccess:", response.body().toString())

                if (response.body() != null) {
                    setTasks(response.body()!!.tasks)
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