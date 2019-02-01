package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.user.jobche.Model.Location
import com.example.user.jobche.Model.Tasks
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private lateinit var email:String

    private lateinit var password: String

    private val size: Int = 20

    private var page: Int = 0

    private val ids = ArrayList<Int>()

    private val titles = ArrayList<String>()

    private val locations = ArrayList<Location>()

    private val dates = ArrayList<String>()

    private val time = ArrayList<String>()

    private val payments = ArrayList<Int>()

    private val numberOfWorkers = ArrayList<Int>()

    private val descriptions = ArrayList<String>()

    private val creatorIds = ArrayList<Int>()

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

    fun getCreatorIds(): ArrayList<Int> {
        return this.creatorIds
    }

    fun getIds(): ArrayList<Int> {
        return this.ids
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
                    val tasks: Tasks = response.body()!!
                    if (!tasks.tasks.isEmpty()) {

                        for (t in tasks.tasks) {
                            getTitles().add(t.title)
                            getDate().add((t.dateTime).substring(8, 10) + "." + (t.dateTime).substring(5, 7))
                            getLocations().add(t.location)
                            getTime().add((t.dateTime).substring(11, 16))
                            getPayments().add(t.payment)
                            getNumberOfWorkers().add(t.numberOfWorkers)
                            getDescriptions().add(t.description)
                            getCreatorIds().add(t.creatorId)
                            getIds().add(t.id)
                        }
                        if (getPage() == 0) {
                            _adapterEventLiveData.call()
                        } else {
                            _updateAdapterEventLiveData.call()
                        }
                    } else {
                        Log.d("RecylerView", "No more tasks to show!")
                        setPage(getPage() - 1)
                    }
                }
            }

        })

    }
}