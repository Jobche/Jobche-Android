package com.example.user.jobche


import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.user.jobche.Model.User

class SignupNameViewModel: BaseObservable() {

    private var firstName:String = ""
    private var lastName:String = ""
    private val registerUser = User()

    private val _nextEventLiveData = SingleLiveData<Any>()

    val nextEventLiveData : LiveData<Any>
        get() = _nextEventLiveData

    fun getRegisterUser() : User {
        return registerUser
    }

    @Bindable
    fun getFirstName(): String {
        return this.firstName
    }

    fun setFirstName(firstName:String) {
        this.firstName = firstName
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    fun getLastName(): String {
        return this.lastName
    }

    fun setLastName(lastName:String) {
        this.lastName = lastName
        notifyPropertyChanged(BR.firstName)
    }

    fun onClick() {
        registerUser.firstName = getFirstName()
        registerUser.lastName = getLastName()
        _nextEventLiveData.call()
    }
}