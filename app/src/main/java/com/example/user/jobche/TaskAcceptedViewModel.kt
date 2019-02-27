package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.Applications
import com.example.user.jobche.Model.UserProfile
import com.example.user.jobche.Model.Work
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Credentials
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskAcceptedViewModel(val task: Task, private val email: String, private val password: String) : BaseObservable() {

    var page: Int = 0

    val size: Int = 20

    @Bindable
    var started = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.started)
        }
    var appliers = ArrayList<UserProfile>()

    var acceptedNames = ArrayList<String>()

    var startWorkersIds = ArrayList<Int>()

    var applications = ArrayList<Application>()

    var acceptedApplications = ArrayList<Application>()


    private val _adapterEventLiveData = SingleLiveData<Any>()

    private val _updateAdapterEventLiveData = SingleLiveData<Any>()

    private val _onClickEventLiveData = SingleLiveData<Any>()

    private val _onStartEventLiveData = SingleLiveData<Any>()


    val adapterEventData: LiveData<Any>
        get() = _adapterEventLiveData

    val updateAdapterEventLiveData: LiveData<Any>
        get() = _updateAdapterEventLiveData


    val onClickEventLiveData: LiveData<Any>
        get() = _onClickEventLiveData

    val onStartEventLiveData: LiveData<Any>
        get() = _onStartEventLiveData


    fun onTaskClick() {
        _onClickEventLiveData.call()
    }

    fun onStart() {
        _onStartEventLiveData.call()

    }

    fun startWork(booleanArray: BooleanArray) {
        started = true
        Log.d("POCHME LI", started.toString())
        for (i in booleanArray.indices) {
            val checked = booleanArray[i]
            if (checked) {
                startWorkersIds.add(acceptedApplications[i].applicant.id)
            }
        }

        val paramObject = JsonObject()
        paramObject.addProperty("taskId", task.id)
        paramObject.add("workers", Gson().toJsonTree(startWorkersIds))

        val call = RetrofitClient().api
            .startWork(Credentials.basic(email, password), paramObject)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("On Start onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("On Start onSuccess", response.body().toString())
            }
        })

    }


    fun getTaskAppliers() {
        val call = RetrofitClient().api
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
                        if (appl.accepted) {
                            acceptedApplications.add(appl)
                            acceptedNames.add(appl.applicant.firstName + " " + appl.applicant.lastName)
//                            applications.remove(appl)
                        }
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

