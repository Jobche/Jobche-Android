package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterUser(var firstName: String? = "",
                        var lastName: String? = "",
                        var email: String? = "",
                        var password: String? = "",
                        var dateOfBirth: DateOfBirth? = null) : Parcelable