package com.example.user.jobche

import android.app.DatePickerDialog
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData

class AddTaskViewModel: BaseObservable() {

    private var date:String = ""

    private var time:String = ""

    private val _dateEventLiveData = SingleLiveData<Any>()

    private val _timeEventLiveData = SingleLiveData<Any>()

    private val _addTaskEventLiveData = SingleLiveData<Any>()

    @Bindable
    fun getDate(): String {
        return this.date
    }

    fun setDate(date:String) {
        this.date = date
        notifyPropertyChanged(BR.date)
    }

    @Bindable
    fun getTime(): String {
        return this.time
    }

    fun setTime(time:String) {
        this.time = time
        notifyPropertyChanged(BR.time)
    }

    val dateEventLiveData : LiveData<Any>
        get() = _dateEventLiveData

    val timeEventLiveData: LiveData<Any>
        get() = _timeEventLiveData

    val addTaskEventLiveData: LiveData<Any>
        get() = _addTaskEventLiveData

    fun onClickDate() {
        _dateEventLiveData.call()
    }

    fun onClickTime() {
        _timeEventLiveData.call()
    }

    fun onClickAddTask() {
        _addTaskEventLiveData.call()
    }

}