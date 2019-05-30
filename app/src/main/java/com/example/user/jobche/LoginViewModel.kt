package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel: ViewModel() {

    var user: User = User()
    var isLogged: Boolean = false
    var toastMsg: String? = null

    private val _signUpEventLiveData = SingleLiveData<Any>()
    private val _loginEventLiveData = SingleLiveData<Any>()
    private val _failEventLiveData = SingleLiveData<Any>()

    val loginEventLiveData: LiveData<Any>
        get() = _loginEventLiveData

    val signUpEventLiveData: LiveData<Any>
        get() = _signUpEventLiveData

    val failEventLiveData: LiveData<Any>
        get() = _failEventLiveData

    fun onSignup() {
        _signUpEventLiveData.call()
    }

    fun onClick() {
        val paramObject = JsonObject()
        paramObject.addProperty("email", user.email)
        paramObject.addProperty("password", user.password)

        val call: Call<User> = RetrofitClient().api
            .loginUser(paramObject)

        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Login user failure:", t.message.toString())
                toastMsg = t.cause.toString()
                _failEventLiveData.call()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("Login user Success:", response.body().toString())
                if (response.body() == null) {
                    toastMsg = "Невалидно въведени данни! Опитайте пак."
                    _failEventLiveData.call()
                } else {
                    user.id = response.body()!!.id
                    isLogged = true
                    _loginEventLiveData.call()

                }
            }
        })
    }
}