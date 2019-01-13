package com.example.user.jobche

import android.app.DatePickerDialog
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData

class AddTaskViewModel: BaseObservable() {

    private var title:String = ""

    private var payment:String = ""

    private var numOfWorkers:String = ""

    private var description: String = ""

    private var date:String = ""

    private var time:String = ""

    private val _dateEventLiveData = SingleLiveData<Any>()

    private val _timeEventLiveData = SingleLiveData<Any>()

    private val _addTaskEventLiveData = SingleLiveData<Any>()

    @Bindable
    fun getTitle(): String {
        return this.title
    }

    fun setTitle(title:String) {
        this.title = title
        notifyPropertyChanged(BR.title)
    }

    @Bindable
    fun getPayment(): String {
        return this.payment
    }

    fun setPayment(payment: String) {
        this.payment = payment
        notifyPropertyChanged(BR.payment)
    }

    @Bindable
    fun getNumOfWorkers(): String {
        return this.numOfWorkers
    }

    fun setNumOfWorkers(numOfWorkers: String) {
        this.numOfWorkers = numOfWorkers
        notifyPropertyChanged(BR.numOfWorkers)
    }

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

    @Bindable
    fun getDescription(): String {
        return this.description
    }

    fun setDescription(description:String) {
        this.description = description
        notifyPropertyChanged(BR.description)
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