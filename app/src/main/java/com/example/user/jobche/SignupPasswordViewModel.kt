package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.User

class SignupPasswordViewModel: BaseObservable() {
    private var password:String = ""
    private var confPassword:String = ""
    private var toastMsg:String = ""
    private var registerUser = User()

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData : LiveData<Any>
        get() = _toastEventLiveData

    val nextEventLiveData : LiveData<Any>
        get() = _nextEventLiveData

    fun getRegisterUser(): User {
        return registerUser
    }

    fun setRegisterUser(user: User){
        this.registerUser = user
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

    fun setToastMsg(toastMsg: String) {
        this.toastMsg = toastMsg
    }

    fun onClick() {
        if(getPassword() == getConfPassword() && getPassword().length >= 6) {
            registerUser.password = getPassword()
            _nextEventLiveData.call()
        } else {
            if (getPassword().length < 6) {
                toastMsg = "Паролата трябва да съдържа поне 6 символа."
            } else if (getPassword() != getConfPassword()) {
                setToastMsg("Паролата не съвпада с потвърдената.")
            }
            _toastEventLiveData.call()
            setPassword("")
            setConfPassword("")
        }


    }
}