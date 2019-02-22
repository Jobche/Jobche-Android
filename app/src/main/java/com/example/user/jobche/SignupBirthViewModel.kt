package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import org.joda.time.LocalDate
import org.joda.time.Years

class SignupBirthViewModel(val registerUser: RegisterUser) : BaseObservable() {

    private var birthDate: LocalDate? = null

    private var formattedBirthDate: String = ""

    var toastMsg: String = ""

    private val _nextEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    private val _birthDateEventLiveData = SingleLiveData<Any>()


    fun getBirthDate(): LocalDate? {
        return this.birthDate
    }

    fun setBirthDate(birthDate: LocalDate) {
        this.birthDate = birthDate
        formatBirthDate(birthDate)
    }

    @Bindable
    fun getFormattedBirthDate(): String {
        return this.formattedBirthDate
    }

    fun setFormattedBirthDate(formattedBirthDate: String) {
        this.formattedBirthDate = formattedBirthDate
        notifyPropertyChanged(BR.formattedBirthDate)
    }

    fun formatBirthDate(birthDate: LocalDate) {
        val formattedBirthDate = String.format("%02d", birthDate.dayOfMonth) + "." +
                String.format("%02d", birthDate.monthOfYear) + "." +
                birthDate.year.toString()
        setFormattedBirthDate(formattedBirthDate)
    }

    fun getYearsOld(): Int {

        val age = Years.yearsBetween(birthDate, LocalDate()).toString()
        return (age.substring(1, age.length - 1)).toInt()
    }

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData

    val birthDateEventLiveData: LiveData<Any>
        get() = _birthDateEventLiveData

    val nextEventLiveData: LiveData<Any>
        get() = _nextEventLiveData

    fun onClickBirthDate() {
        _birthDateEventLiveData.call()
    }

    fun onClick() {
        if (getBirthDate() == null) {
            toastMsg = "Попълнете празните полета."
            _toastEventLiveData.call()
        }
        else if(getYearsOld() < 18) {
            toastMsg = "Трябва да си поне 18 годишен."
            _toastEventLiveData.call()
        }
        else {
            registerUser.dateOfBirth = getBirthDate()!!
            _nextEventLiveData.call()
        }
    }
}