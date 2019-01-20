package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.user.jobche.Model.Location
import com.example.user.jobche.Model.Tasks
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel {
    private var page: Int = 0

    private val size: Int = 10

    private val titles = ArrayList<String>()

    private val locations = ArrayList<Location>()

    private val dates = ArrayList<String>()

    private val time = ArrayList<String>()

    private val payments = ArrayList<Int>()

    private val numberOfWorkers = ArrayList<Int>()

    private val descriptions = ArrayList<String>()

    private val _fabEventLiveData = SingleLiveData<Any>()

    private val _adapterEventLiveData = SingleLiveData<Any>()


    fun getTitles(): ArrayList<String> {
        return this.titles
    }

    fun getLocations(): ArrayList<Location> {
        return this.locations
    }

    fun getDate(): ArrayList<String> {
        return this.dates
    }

    fun getTime(): ArrayList<String> {
        return this.time
    }

    fun getPayments(): ArrayList<Int> {
        return this.payments
    }

    fun getNumberOfWorkers(): ArrayList<Int> {
        return this.numberOfWorkers
    }


    fun getDescriptions(): ArrayList<String> {
        return this.descriptions
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
                        getTitles().add(t.title)
                        getDate().add((t.dateTime).substring(8, 10) + "." + (t.dateTime).substring(5, 7))
                        getLocations().add(t.location)
                        getTime().add((t.dateTime).substring(11, 16))
                        getPayments().add(t.payment)
                        getNumberOfWorkers().add(t.numberOfWorkers)
                        getDescriptions().add(t.description)
                    }
                    _adapterEventLiveData.call()
                }


            }

        })
    }
}