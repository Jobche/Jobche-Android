package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import android.util.Patterns
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupContactsViewModel : BaseObservable() {


    private var phoneNum: String = ""

    private var email: String = ""

    private lateinit var registerUser:User

    private var toastMsg: String = ""

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    @Bindable
    fun getPhoneNum(): String {
        return this.phoneNum
    }

    fun setPhoneNum(phoneNum: String) {
        this.phoneNum = phoneNum
        notifyPropertyChanged(BR.phoneNum)
    }

    @Bindable
    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    fun setRegisterUser(user: User) {
        this.registerUser = user
    }


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

        if (getEmail() == "" || getPhoneNum() == "") {
            setToastMsg("Попълнете празните полета.")
            _toastEventLiveData.call()
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())) {
            setToastMsg("Неправилно въведен Имейл.")
            _toastEventLiveData.call()
        } else {

            registerUser.email = getEmail()
            registerUser.phoneNum = getPhoneNum()

            val paramObject = JsonObject()
            paramObject.addProperty("firstName", registerUser.firstName)
            paramObject.addProperty("lastName", registerUser.lastName)
            paramObject.addProperty("password", registerUser.password)
            paramObject.add(
                "dateOfBirth",
                Gson().toJsonTree(
                    DateOfBirth(
                        registerUser.dateOfBirth!!.dayOfMonth,
                        registerUser.dateOfBirth!!.monthOfYear,
                        registerUser.dateOfBirth!!.year
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