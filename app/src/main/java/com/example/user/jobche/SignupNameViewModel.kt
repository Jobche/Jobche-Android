package com.example.user.jobche


import android.arch.lifecycle.LiveData

class SignupNameViewModel(private val registerUser: RegisterUser) {

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
        if (registerUser.firstName != "" && registerUser.lastName != "") {
            _nextEventLiveData.call()
        } else {
            setToastMsg("Попълнете празните полета.")
            _toastEventLiveData.call()

        }
    }
}