package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskAcceptedViewModel(val task: Task, private val email: String, private val password: String) :
    BaseObservable() {

    var page: Int = 0

    val size: Int = 20

    var workId: Long = 0

    @Bindable
    var started = false
        set(value) {
            field = value
            _hasStartedEventLiveData.call()
            notifyPropertyChanged(BR.started)
        }

    var acceptedNames = ArrayList<String>()

    var startWorkersIds = ArrayList<Long>()

    var reviewWorkers = ArrayList<UserProfile>()

    var applications = ArrayList<Application>()

    var acceptedApplications = ArrayList<Application>()

    val appliers = ArrayList<UserProfile>()

    val acceptedAppliers = ArrayList<UserProfile>()

    private val _adapterEventLiveData = SingleLiveData<Any>()

    private val _updateAdapterEventLiveData = SingleLiveData<Any>()

    private val _onClickEventLiveData = SingleLiveData<Any>()

    private val _onStartEventLiveData = SingleLiveData<Any>()

    private val _hasStartedEventLiveData = SingleLiveData<Any>()

    private val _onReviewsEventLiveData = SingleLiveData<Any>()


    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData

    val onClickEventLiveData: LiveData<Any>
        get() = _onClickEventLiveData

    val onStartEventLiveData: LiveData<Any>
        get() = _onStartEventLiveData

    val hasStartedEventLiveData: LiveData<Any>
        get() = _hasStartedEventLiveData

    val onReviewsEventLiveData: LiveData<Any>
        get() = _onReviewsEventLiveData


    fun onTaskClick() {
        _onClickEventLiveData.call()
    }

    fun onStart() {
        if (!started) {
            _onStartEventLiveData.call()
        } else {
            endWork()
        }
    }

    fun endWork() {

        val call = RetrofitClient().api
            .endWork(Credentials.basic(email, password), workId, Status.ENDED)

        call.enqueue(object : Callback<Work> {
            override fun onFailure(call: Call<Work>, t: Throwable) {
                Log.d("End work onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<Work>, response: Response<Work>) {
                Log.d("End work onSuccess", response.body().toString())
            }
        })
        started = false
        _onReviewsEventLiveData.call()
    }

    fun startWork(booleanArray: BooleanArray) {

        for (i in booleanArray.indices) {
            val checked = booleanArray[i]
            if (checked) {
                startWorkersIds.add(acceptedAppliers[i].id)
            }
        }

        val paramObject = JsonObject()
        paramObject.addProperty("taskId", task.id)
        paramObject.add("workers", Gson().toJsonTree(startWorkersIds))

        val call = RetrofitClient().api
            .startWork(Credentials.basic(email, password), paramObject)

        call.enqueue(object : Callback<Work> {
            override fun onFailure(call: Call<Work>, t: Throwable) {
                Log.d("On Start onFailure ", t.message.toString())
                started = false
            }

            override fun onResponse(call: Call<Work>, response: Response<Work>) {
                Log.d("On Start onSuccess", response.body().toString())
                if (response.body() != null) {
                    workId = response.body()!!.id
                    started = true
                    reviewWorkers = response.body()!!.workers
                }
                else {
                    started = false
                }
            }
        })

    }

    fun getTaskAppliers() {
        val call = RetrofitClient().api
            .getAppliers(Credentials.basic(email, password), task.id, page, size)

        call.enqueue(object : Callback<Applications> {
            override fun onFailure(call: retrofit2.Call<Applications>, t: Throwable) {
                Log.d("My Appliers onFailure ", t.message.toString())
            }

            override fun onResponse(call: retrofit2.Call<Applications>, response: Response<Applications>) {
                Log.d("My Appliers onSuccess", response.body().toString())
                if (response.body() != null) {
                    for (application in response.body()!!.applications) {
                        if (application.accepted) {
                            acceptedAppliers.add(application.applicant)
                            acceptedApplications.add(application)
                            acceptedNames.add(application.applicant.firstName + " " + application.applicant.lastName)
                        }else {
                            appliers.add(application.applicant)
                            applications.add(application)
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
                    Log.d("RecylerView", "No more people to show!")
                    page--
                }
            }
        })
    }

}

