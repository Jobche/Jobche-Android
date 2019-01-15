package com.example.user.jobche

import android.content.Intent
import androidx.lifecycle.LiveData
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.UI.AddTaskActivity

class HomeViewModel {
    private var email:String = ""

    private var password:String = ""

    private val _fabEventLiveData = SingleLiveData<Any>()

    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPassword(): String {
        return this.password
    }

    fun setPassword(password: String) {
        this.password = password
    }
    val fabEventLiveData : LiveData<Any>
        get() = _fabEventLiveData


    fun onClickFab() {
        _fabEventLiveData.call()
    }
}