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

    val time: String = (task.dateTime).substring(11, 16)

    val isVisible: Boolean = (task.creator.id == userId)

    val onClickEventLiveData = SingleLiveData<Any>()

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
                onClickEventLiveData.call()
            }

        })
    }
}