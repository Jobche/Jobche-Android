package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.RegisterUser

class SignupPasswordViewModel: BaseObservable() {
    private var password:String = ""
    private var confPassword:String = ""
    private var toastMsg:String = ""
    private var registerUser = RegisterUser()

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData : LiveData<Any>
        get() = _toastEventLiveData


    val nextEventLiveData : LiveData<Any>
        get() = _nextEventLiveData

    fun getRegisterUser(): RegisterUser {
        return registerUser
    }

    fun setRegisterUser(registerUser: RegisterUser){
        this.registerUser = registerUser
    }


    @Bindable
    fun getPassword(): String {
        return this.password
    }

    fun setPassword(password:String) {
        this.password = password
        notifyPropertyChanged(BR.password)
    }

    @Bindable
    fun getConfPassword(): String {
        return this.confPassword
    }

    fun setConfPassword(confPassword:String) {
        this.confPassword = confPassword
        notifyPropertyChanged(BR.confPassword)
    }

    fun getToastMsg(): String {
        return toastMsg
    }

    fun onClick() {
        if(getPassword() == getConfPassword() && getPassword().length >= 6) {
            registerUser.password = getPassword()
            Log.d("PASSSOWRODOWDR", registerUser.password)
            _nextEventLiveData.call()
        } else {
            if (getPassword().length < 6) {
                toastMsg = "Password must be at least 6 characters"
            } else if (getPassword() != getConfPassword()) {
                toastMsg = "Password does not match the confirm password."
            }
            _toastEventLiveData.call()
            setPassword("")
            setConfPassword("")
        }


    }
}