package com.example.user.jobche

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.user.jobche.Model.Task
import com.example.user.jobche.Model.Tasks
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel {
    private var email: String = ""

    private var password: String = ""

    private var page: Int = 0

    private val size: Int = 10

    private val titles = ArrayList<String>()

    private val locations = ArrayList<String>()

    private val dates = ArrayList<String>()

    private val time = ArrayList<String>()

    private val payments = ArrayList<String>()

    private val numberOfWorkers = ArrayList<String>()



    private val _fabEventLiveData = SingleLiveData<Any>()

    private val _adapterEventLiveData = SingleLiveData<Any>()

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

    fun getTitles(): ArrayList<String> {
        return this.titles
    }

    fun getLocations(): ArrayList<String> {
        return this.locations
    }

    fun getDate(): ArrayList<String> {
        return this.dates
    }

    fun getTime(): ArrayList<String> {
        return this.time
    }

    fun getPayments(): ArrayList<String> {
        return this.payments
    }

    fun getNumberOfWorkers(): ArrayList<String> {
        return this.numberOfWorkers
    }


    val fabEventLiveData: LiveData<Any>
        get() = _fabEventLiveData

    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    fun onClickFab() {
        _fabEventLiveData.call()
    }

    fun generateTasks() {
        val authToken = Credentials.basic("string", "string")

        val call: Call<Tasks> = RetrofitClient().getApi()
            .getTasks(authToken, page, size)

        call.enqueue(object : Callback<Tasks> {
            override fun onFailure(call: Call<Tasks>, t: Throwable) {
                Log.d("Add Task onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<Tasks>, response: Response<Tasks>) {
                Log.d("Add Task onSuccess:", response.body().toString())

                if(response.body() != null) {
                    val tasks:Tasks = response.body()!!
                    for(t in tasks.tasks) {
                        titles.add(t.title)
                        dates.add((t.dateTime).substring(8, 10) + "." + (t.dateTime).substring(5, 7))
                        locations.add(t.location.city)
                        time.add((t.dateTime).substring(11, 16))
                        payments.add(t.payment.toString())
                        numberOfWorkers.add(t.numberOfWorkers.toString())
                    }
                    _adapterEventLiveData.call()
                }


            }

        })
    }
}