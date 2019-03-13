package com.example.user.jobche

import android.os.Parcelable
import com.example.user.jobche.Model.DateOfBirth
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
     var id: Long = 0,
     var firstName: String = "",
     var lastName: String = "",
     var password: String = "",
     var dateOfBirth: DateOfBirth?  = null,
     var email: String = "",
     var phoneNum: String = ""
) : Parcelable