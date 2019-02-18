package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.LocalDate

@Parcelize
data class User(
     val id: Int = 0,
     var firstName: String = "",
     var lastName: String = "",
     var password: String = "",
     var dateOfBirth: DateOfBirth?  = null,
     var email: String = "",
     var phoneNum: String = ""
) : Parcelable