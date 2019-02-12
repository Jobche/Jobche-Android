package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Filter(
    val title: String?,
    val city: String?,
    val dateStart: String?,
    val dateEnd: String?,
    val numWStart: Int?,
    val pStart: Int?
) : Parcelable
