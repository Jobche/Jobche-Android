package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.User
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


data class LoginViewModel(
    var email: String = "",
    var password: String = "",
    var id:Int = 0,
    var isLogged:Boolean = false,
    var toastMsg:String = ""
){

    private val _signupEventLiveData = SingleLiveData<Any>()

    private val _loginEventLiveData = SingleLiveData<Any>()

    private val _failEventLiveData = SingleLiveData<Any>()


    val loginEventLiveData: LiveData<Any>
        get() = _loginEventLiveData

    val signupEventLiveData: LiveData<Any>
        get() = _signupEventLiveData

    val failEventLiveData: LiveData<Any>
        get() = _failEventLiveData

    fun onSignup() {
        _signupEventLiveData.call()
    }

    fun onClick() {
        val paramObject = JsonObject()
        paramObject.addProperty("email", email)
        paramObject.addProperty("password", password)

        val call: Call<User> = RetrofitClient().getApi()
            .loginUser(paramObject)

        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("Login user failure:", t.message.toString())
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("Login user Success:", response.body().toString())
                val user: User? = response.body()
                if (user == null) {
                    toastMsg = "Невалидно въведени данни! Опитайте пак."
                    _failEventLiveData.call()
                } else {
                    id = user.id
                    isLogged = true
                    _loginEventLiveData.call()

                }
            }

        })

    }

}