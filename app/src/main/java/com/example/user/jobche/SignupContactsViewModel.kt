package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.util.Log
import android.util.Patterns
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupContactsViewModel(val registerUser: RegisterUser) {


    private var toastMsg: String = ""

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    fun getToastMsg(): String {
        return toastMsg
    }

    fun setToastMsg(toastMsg: String) {
        this.toastMsg = toastMsg
    }

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData

    val nextEventLiveData: LiveData<Any>
        get() = _nextEventLiveData


    fun onClick() {

        if (registerUser.email == "" || registerUser.phoneNum == "") {
            setToastMsg("Попълнете празните полета.")
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(registerUser.email).matches())) {
            setToastMsg("Неправилно въведен Имейл.")
        } else if (registerUser.phoneNum.length != 10) {
            setToastMsg("Неправилно въведен Телефон.")
        }

        if (getToastMsg() != "") {
            _toastEventLiveData.call()
        } else {

            val paramObject = JsonObject()
            paramObject.addProperty("firstName", registerUser.firstName)
            paramObject.addProperty("lastName", registerUser.lastName)
            paramObject.addProperty("password", registerUser.password)
            paramObject.add(
                "dateOfBirth",
                Gson().toJsonTree(
                    DateOfBirth(
                        registerUser.dateOfBirth.dayOfMonth,
                        registerUser.dateOfBirth.monthOfYear,
                        registerUser.dateOfBirth.year
                    )
                )
            )
            paramObject.addProperty("email", registerUser.email)
            paramObject.addProperty("phoneNum", registerUser.phoneNum)


            val call: Call<User> = RetrofitClient().getApi()
                .createUser(paramObject)

            call.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("Sign up onFailure: ", t.message.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.d("Sign up onSuccess:", response.body().toString())
                    _nextEventLiveData.call()
                }

            })
        }
    }
}