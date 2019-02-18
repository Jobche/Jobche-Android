package com.example.user.jobche

import android.arch.lifecycle.LiveData


class SignupPasswordViewModel(val user: RegisterUser) {

    private var toastMsg: String = ""

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData

    val nextEventLiveData: LiveData<Any>
        get() = _nextEventLiveData

    fun getToastMsg(): String {
        return toastMsg
    }

    fun setToastMsg(toastMsg: String) {
        this.toastMsg = toastMsg
    }

    fun onClick() {
        if (user.password != user.confPassword) {
            setToastMsg("Паролата не съвпада с потвърдената.")
        } else if (user.password.length < 6) {
            setToastMsg("Паролата трябва да съдържа поне 6 символа.")
        }

        if (getToastMsg() != "") {
            _toastEventLiveData.call()
            user.password = ""
            user.password = ""
        } else {
            _nextEventLiveData.call()
        }
    }
}