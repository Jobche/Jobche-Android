package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.user.jobche.Model.Application
import com.google.gson.JsonObject
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenedTaskViewModel(val task: Task) {

    private lateinit var email: String

    private lateinit var password: String


    private val date: String = (task.safeDateTime).substring(8, 10) + "." + (task.safeDateTime).substring(5, 7)

    private val time: String = (task.safeDateTime).substring(11, 16)
    private val payment: String = task.safePayment.toString()

    private val numberOfWorkers: String = task.safeNumberOfWorkers.toString()

    private val _onClickEventLiveData = SingleLiveData<Any>()


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

    fun getDate(): String {
        return this.date
    }

    fun getTime(): String {
        return this.time
    }

    fun getPayment(): String {
        return this.payment
    }

    fun getNumberOfWorkers(): String {
        return this.numberOfWorkers
    }


    val onClickEventLiveData: LiveData<Any>
        get() = _onClickEventLiveData


    fun onClick() {
        val paramObject = JsonObject()
        paramObject.addProperty("taskId", task.safeId)

        val authToken = Credentials.basic(getEmail(), getPassword())

        val call: Call<Application> = RetrofitClient().getApi()
            .applyForTask(authToken, paramObject)

        call.enqueue(object : Callback<Application> {
            override fun onFailure(call: Call<Application>, t: Throwable) {
                Log.d("Apply Task onFailure ", t.message.toString())
            }

            override fun onResponse(call: Call<Application>, response: Response<Application>) {
                Log.d("Apply Task onSuccess", response.body().toString())
                _onClickEventLiveData.call()
            }

        })
    }
}