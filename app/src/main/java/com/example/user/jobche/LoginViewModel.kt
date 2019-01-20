package com.example.user.jobche

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import com.example.user.jobche.Model.LoginUser
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel: BaseObservable() {

    private var email:String = ""
    private var password:String = ""

    private val _signupEventLiveData = SingleLiveData<Any>()

    private val _loginEventLiveData = SingleLiveData<Any>()

    val loginEventLiveData : LiveData<Any>
        get() = _loginEventLiveData


    val signupEventLiveData : LiveData<Any>
        get() = _signupEventLiveData


    fun onSignup() {
        _signupEventLiveData.call()
    }
    @Bindable
    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email:String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    @Bindable
    fun getPassword(): String {
        return this.password
    }

    fun setPassword(password:String) {
        this.password = password
        notifyPropertyChanged(BR.password)
    }

    fun onClick() {
        val paramObject = JsonObject()
        paramObject.addProperty("email", this.email)
        paramObject.addProperty("password", this.password)

        val call: Call<LoginUser> = RetrofitClient().getApi()
            .loginUser(paramObject)

        call.enqueue(object: Callback<LoginUser> {
            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                Log.d("Login user failure:", t.message.toString())
            }

            override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                Log.d("Login user Success:", response.body().toString())

            }

        })
        _loginEventLiveData.call()

    }

}