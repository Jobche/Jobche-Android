package com.example.user.jobche

import android.content.Intent
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.UI.SignupNameActivity
import com.example.user.jobche.UI.SignupPasswordActivity

class SignupNameViewModel: BaseObservable() {

    private var firstName:String = ""
    private var lastName:String = ""
    private val registerUser = RegisterUser()

    private val _nextEventLiveData = SingleLiveData<Any>()

    val nextEventLiveData : LiveData<Any>
        get() = _nextEventLiveData

    fun getRegisterUser() : RegisterUser {
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