package com.example.user.jobche.ViewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.user.jobche.Interface.LoginResultCallBacks

class LoginViewModelFactory (private val listener: LoginResultCallBacks): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(listener) as T
    }
}