package com.example.user.jobche.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterUser(var firstName: String = "",
                        var lastName: String = "",
                        var email: String = "",
                        var password: String = "") : Parcelable