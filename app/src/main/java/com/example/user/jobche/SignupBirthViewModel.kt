package com.example.user.jobche

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import com.example.user.jobche.Model.RegisterUser
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupBirthViewModel: BaseObservable() {

//    private var birthDate:String = ""
    private var email:String = ""

    private var registerUser = RegisterUser()

    private val _nextEventLiveData = SingleLiveData<Any>()

    @Bindable
    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email:String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    fun getRegisterUser(): RegisterUser {
        return registerUser
    }

    fun setRegisterUser(registerUser: RegisterUser){
        this.registerUser = registerUser
    }

    val nextEventLiveData : LiveData<Any>
        get() = _nextEventLiveData


    fun onClick() {
        registerUser.email = getEmail()

        val paramObject = JsonObject()
        paramObject.addProperty("firstName", registerUser.firstName)
        paramObject.addProperty("lastName", registerUser.lastName)
        paramObject.addProperty("email", registerUser.email)
        paramObject.addProperty("password", registerUser.password)

        val call: Call<RegisterUser> = RetrofitClient().getApi()
            .createUser(paramObject)

        call.enqueue(object: Callback<RegisterUser> {
            override fun onFailure(call: Call<RegisterUser>, t: Throwable) {
                Log.d("Sign up onFailure: ", t.message.toString())
            }

            override fun onResponse(call: Call<RegisterUser>, response: Response<RegisterUser>) {
                Log.d("Sign up onSuccess:", response.body().toString())
                _nextEventLiveData.call()
            }

        })

    }
}