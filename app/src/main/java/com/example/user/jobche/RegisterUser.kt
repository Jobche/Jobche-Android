package com.example.user.jobche

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate

@Parcelize
data class RegisterUser(
    private val _id: Int = 0,
    private var _firstName: String = "",
    private var _lastName: String = "",
    private var _password: String = "",
    private var _confPassword: String = "",
    private var _dateOfBirth: LocalDate? = null,
    private var _email: String = "",
    private var _phoneNum: String = ""
) : Parcelable, BaseObservable() {

    val id: Int
        get() = _id

    var firstName: String
        @Bindable get() = _firstName
        set(value) {
            _firstName = value
            notifyPropertyChanged(BR.firstName)
        }

    var lastName: String
        @Bindable get() = _lastName
        set(value) {
            _lastName = value
            notifyPropertyChanged(BR.lastName)
        }

    var password: String
        @Bindable get() = _password
        set(value) {
            _password = value
            notifyPropertyChanged(BR.password)
        }

    var confPassword: String
        @Bindable get() = _confPassword
        set(value) {
            _confPassword = value
            notifyPropertyChanged(BR.confPassword)
        }

    var dateOfBirth: LocalDate
        get() = _dateOfBirth!!
        set(value) {
            _dateOfBirth = value
        }

    var email: String
        @Bindable get() = _email
        set(value) {
            _email = value
            notifyPropertyChanged(BR.email)
        }

    var phoneNum: String
        @Bindable get() = _phoneNum
        set(value) {
            _phoneNum = value
            notifyPropertyChanged(BR.phoneNum)
        }
}
