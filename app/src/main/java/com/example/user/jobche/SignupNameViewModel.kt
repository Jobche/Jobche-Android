package com.example.user.jobche


import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.user.jobche.Model.User

class SignupNameViewModel : BaseObservable() {

    private var firstName: String = ""
    private var lastName: String = ""
    private val registerUser = User()
    private var toastMsg: String = ""


    private val _nextEventLiveData = SingleLiveData<Any>()
    private val _toastEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData


    val nextEventLiveData: LiveData<Any>
        get() = _nextEventLiveData

    fun getRegisterUser(): User {
        return registerUser
    }

    @Bindable
    fun getFirstName(): String {
        return this.firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    fun getLastName(): String {
        return this.lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
        notifyPropertyChanged(BR.firstName)
    }

    fun getToastMsg(): String {
        return toastMsg
    }

    fun setToastMsg(toastMsg: String) {
        this.toastMsg = toastMsg
    }

    fun onClick() {
        if (getFirstName() != "" && getLastName() != "") {
            registerUser.firstName = getFirstName()
            registerUser.lastName = getLastName()
            _nextEventLiveData.call()
        } else {
            setToastMsg("Попълнете празните полета.")
            _toastEventLiveData.call()

        }
    }
}