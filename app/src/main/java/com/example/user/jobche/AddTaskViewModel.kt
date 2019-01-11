package com.example.user.jobche

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData

class AddTaskViewModel: BaseObservable() {

    private val _dateEventLiveData = SingleLiveData<Any>()

    private val _timeEventLiveData = SingleLiveData<Any>()

    private val _addTaskEventLiveData = SingleLiveData<Any>()

    val dateEventLiveData : LiveData<Any>
        get() = _dateEventLiveData

    val timeEventLiveData: LiveData<Any>
        get() = _timeEventLiveData

    val addTaskEventLiveData: LiveData<Any>
        get() = _addTaskEventLiveData


    fun onClickAddTask() {
        _addTaskEventLiveData.call()
    }

}