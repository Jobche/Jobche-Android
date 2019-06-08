package com.example.user.jobche


import android.arch.lifecycle.LiveData

class SignupNameViewModel(val registerUser: RegisterUser) {

    var toastMsg: String = ""

    val nextEventLiveData = SingleLiveData<Any>()
    val toastEventLiveData = SingleLiveData<Any>()

    fun onClick() {
        if (registerUser.firstName != "" && registerUser.lastName != "") {
            nextEventLiveData.call()
        } else {
            toastMsg = "Попълнете празните полета."
            toastEventLiveData.call()

        }
    }
}