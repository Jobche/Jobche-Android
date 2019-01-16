package com.example.user.jobche

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.Model.Task
import com.example.user.jobche.UI.AddTaskActivity
import okhttp3.Credentials
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel {
    private var email:String = ""

    private var password:String = ""

    private val _fabEventLiveData = SingleLiveData<Any>()

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
    val fabEventLiveData : LiveData<Any>
        get() = _fabEventLiveData


    fun onClickFab() {
        _fabEventLiveData.call()
    }

    fun generateTasks() {
        val authToken = Credentials.basic("string", "string")


        val call: Call<Task> = RetrofitClient().getApi()
            .getTasks(authToken,1,10)

        call.enqueue(object: Callback<Task> {
            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.d("Add Task onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                Log.d("Add Task onSuccess:", response.body().toString())

            }

        })
    }
}