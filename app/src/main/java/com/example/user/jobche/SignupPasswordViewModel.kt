package com.example.user.jobche

import android.arch.lifecycle.LiveData


class SignupPasswordViewModel(val registerUser: RegisterUser) {

    var toastMsg: String = ""

    val nextEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData = SingleLiveData<Any>()

    fun onClick() {
        if (registerUser.password != registerUser.confPassword) {
            toastMsg = "Паролата не съвпада с потвърдената."
        } else if (registerUser.password.length < 6) {
            toastMsg = "Паролата трябва да съдържа поне 6 символа."
        }else {
            toastMsg = ""
        }

        if (toastMsg != "") {
            toastEventLiveData.call()
            registerUser.password = ""
            registerUser.confPassword = ""
        } else {
            nextEventLiveData.call()
        }
    }
}