package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.util.Log
import com.example.user.jobche.Model.Application
import com.google.gson.JsonObject
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenedTaskViewModel(val task: Task, private val email: String, private val password: String, private val userId: Long) {

    val date: String = (task.dateTime).substring(8, 10) + "." + (task.dateTime).substring(5, 7)
//    val date: String = task.dateTime!!.dayOfMonth.toString() + "." + task.dateTime!!.monthOfYear.toString()


    val time: String = (task.dateTime).substring(11, 16)
//    val time: String = task.dateTime!!.hourOfDay.toString() + ":" + task.dateTime!!.minuteOfHour.toString()

    val isVisible: Boolean = (task.creatorId == userId)

    private val _onClickEventLiveData = SingleLiveData<Any>()


    val onClickEventLiveData: LiveData<Any>
        get() = _onClickEventLiveData


    fun onClick() {
        val paramObject = JsonObject()
        paramObject.addProperty("taskId", task.id)

        val call: Call<Application> = RetrofitClient().api
            .applyForTask(Credentials.basic(email, password), paramObject)

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