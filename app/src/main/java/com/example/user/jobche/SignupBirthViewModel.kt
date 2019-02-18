package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.example.user.jobche.Model.DateOfBirth
import com.example.user.jobche.Model.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.Years
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupBirthViewModel : BaseObservable() {

    private var birthDate: LocalDate? = null

    private var formattedBirthDate: String = ""

    private var registerUser = User()

    private var toastMsg: String = ""

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

    fun setRegisterUser(user: User) {
        this.registerUser = user
    }

    fun getRegisterUser(): User {
        return this.registerUser
    }

    fun getToastMsg(): String {
        return toastMsg
    }

    fun setToastMsg(toastMsg: String) {
        this.toastMsg = toastMsg
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
            setToastMsg("Попълнете празните полета.")
            _toastEventLiveData.call()
        }
        else if(getYearsOld() < 18) {
            setToastMsg("Трябва да си поне 18 годишен.")
            _toastEventLiveData.call()
        }
        _nextEventLiveData.call()
    }
}