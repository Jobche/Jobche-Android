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

    val nextEventLiveData = SingleLiveData<Any>()

    val toastEventLiveData = SingleLiveData<Any>()

    val birthDateEventLiveData = SingleLiveData<Any>()


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

    fun onClickBirthDate() {
        birthDateEventLiveData.call()
    }

    fun onClick() {
        if (birthDate == null) {
            toastMsg = "Попълнете празните полета."
            toastEventLiveData.call()
        }
        else if(getYearsOld() < 18) {
            toastMsg = "Трябва да си поне 18 годишен."
            toastEventLiveData.call()
        }
        else {
            registerUser.dateOfBirth = birthDate!!
            nextEventLiveData.call()
        }
    }
}