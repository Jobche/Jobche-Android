package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterUser(val firstName: String,
                        val lastName: String,
                        val email: String,
                        val passoword: String) : Parcelable