package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.RegisterUser
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupBirthViewModel: BaseObservable() {

    private lateinit var birthDate:DateOfBirth

    private var formattedBirthDate: String = ""

    private var email:String = ""

    private var registerUser = RegisterUser()

    private val _birthDateEventLiveData = SingleLiveData<Any>()

    private val _nextEventLiveData = SingleLiveData<Any>()

    fun getBirthDate(): DateOfBirth {
        return this.birthDate
    }

    fun setBirthDate(birthDate:DateOfBirth) {
        this.birthDate = birthDate
        formatBirthDate(birthDate)
    }

    @Bindable
    fun getFormattedBirthDate(): String {
        return this.formattedBirthDate
    }

    fun setFormattedBirthDate(formattedBirthDate: String) {
        this.formattedBirthDate = formattedBirthDate
        notifyPropertyChanged(BR.formattedBirthDate)
    }


    fun formatBirthDate(birthDate: DateOfBirth) {
        val formattedBirthDate = birthDate.day.toString() + "." +
                                    birthDate.month.toString() + "." +
                                    birthDate.year.toString()
        setFormattedBirthDate(formattedBirthDate)
    }

    @Bindable
    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email:String) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    fun setRegisterUser(registerUser: RegisterUser){
        this.registerUser = registerUser
    }

    val birthDateEventLiveData: LiveData<Any>
        get() = _birthDateEventLiveData

    fun onClickBirthDate() {
        _birthDateEventLiveData.call()
    }

    val nextEventLiveData : LiveData<Any>
        get() = _nextEventLiveData



    fun onClick() {
        registerUser.email = getEmail()
        registerUser.dateOfBirth = getBirthDate()

        val paramObject = JsonObject()
        paramObject.addProperty("firstName", registerUser.firstName)
        paramObject.addProperty("lastName", registerUser.lastName)
        paramObject.addProperty("email", registerUser.email)
        paramObject.addProperty("password", registerUser.password)
        paramObject.add("dateOfBirth", Gson().toJsonTree(DateOfBirth(birthDate.day, birthDate.month, birthDate.month)))


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