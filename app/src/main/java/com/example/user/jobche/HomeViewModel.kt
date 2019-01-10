package com.example.user.jobche

import android.content.Intent
import androidx.lifecycle.LiveData
import com.example.user.jobche.Model.RegisterUser
import com.example.user.jobche.UI.AddTaskActivity

class HomeViewModel {

    private val _fabEventLiveData = SingleLiveData<Any>()

    val fabEventLiveData : LiveData<Any>
        get() = _fabEventLiveData


    fun onClickFab() {
        _fabEventLiveData.call()
    }
}