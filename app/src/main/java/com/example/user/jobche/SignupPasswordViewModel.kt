package com.example.user.jobche

import android.arch.lifecycle.LiveData


class SignupPasswordViewModel(val registerUser: RegisterUser) {

    var toastMsg: String = ""

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData

    val nextEventLiveData: LiveData<Any>
        get() = _nextEventLiveData


    fun onClick() {
        if (registerUser.password != registerUser.confPassword) {
            toastMsg = "Паролата не съвпада с потвърдената."
        } else if (registerUser.password.length < 6) {
            toastMsg = "Паролата трябва да съдържа поне 6 символа."
        }else {
            toastMsg = ""
        }

        if (toastMsg != "") {
            _toastEventLiveData.call()
            registerUser.password = ""
            registerUser.confPassword = ""
        } else {
            _nextEventLiveData.call()
        }
    }
}