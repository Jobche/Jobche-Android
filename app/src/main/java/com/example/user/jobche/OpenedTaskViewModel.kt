package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils.substring
import android.util.Log
import com.example.user.jobche.Model.Application
import com.example.user.jobche.Model.Task
import com.example.user.jobche.Model.UserProfile
import com.google.gson.JsonObject
import okhttp3.Credentials
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenedTaskViewModel(val task: Task) {

    private lateinit var email: String

    private lateinit var password: String

    private val date: String = substring(task.dateTime, 0, 5)

    private val time: String = substring(task.dateTime, 5, task.dateTime.length)

    private val payment: String = task.payment.toString()

    private val numberOfWorkers: String = task.numberOfWorkers.toString()

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


//    fun createUser() : UserProfile {
//
//        val paramObject = JsonObject()
//        paramObject.addProperty("id", task.creatorId)
//
//        val authToken = Credentials.basic(getEmail(), getPassword())
//
//
//        val call: Call<UserProfile> = RetrofitClient().getApi()
//            .getUser(authToken, task.creatorId)
//
//        call.enqueue(object : Callback<UserProfile> {
//            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
//                Log.d("Get User onFailure ", t.message.toString())
//            }
//
//            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
//                Log.d("Get User onSuccess", response.body().toString())
//                if (response.body() != null) {
//                   user = (response.body()!!)
//                }
//
//            }
//        })
//        return user
//    }

    val onClickEventLiveData: LiveData<Any>
        get() = _onClickEventLiveData


    fun onClick() {
        val paramObject = JsonObject()
        paramObject.addProperty("taskid", task.id)

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