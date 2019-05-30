package com.example.user.jobche


import android.arch.lifecycle.LiveData

class SignupNameViewModel(val registerUser: RegisterUser) {

    var toastMsg: String = ""

    private val _nextEventLiveData = SingleLiveData<Any>()
    private val _toastEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData

    val nextEventLiveData: LiveData<Any>
        get() = _nextEventLiveData

    fun onClick() {
        if (registerUser.firstName != "" && registerUser.lastName != "") {
            _nextEventLiveData.call()
        } else {
            toastMsg = "Попълнете празните полета."
            _toastEventLiveData.call()

        }
    }
}