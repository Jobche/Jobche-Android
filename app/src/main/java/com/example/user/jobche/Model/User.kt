package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime
import org.joda.time.LocalDate

@Parcelize
data class User(var id:Int = 0,
                var firstName: String? = "",
                var lastName: String? = "",
                var email: String? = "",
                var password: String? = "",
                var dateOfBirth: LocalDate? = null) : Parcelable