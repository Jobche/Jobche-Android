package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel;
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.Applications
import com.example.user.jobche.Model.UserProfile
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyApplicationsViewModel : ViewModel() {

    private var page: Int = 0

    private val size: Int = 20

    private lateinit var email: String

    private lateinit var password: String

    var tasks = ArrayList<Task>()


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

    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData


    fun getAppliedTasks() {
        val call: Call<Applications> = RetrofitClient().api
            .getMyApplications(getAuthToken(), getPage(), getSize())

        call.enqueue(object : Callback<Applications> {
            override fun onFailure(call: Call<Applications>, t: Throwable) {
                Log.d("My Apply onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<Applications>, response: Response<Applications>) {
                Log.d("My Apply onSuccess", response.body().toString())
                if (response.body() != null) {
                    for (appl in response.body()!!.applications) {
                        tasks.add(appl.task)
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
