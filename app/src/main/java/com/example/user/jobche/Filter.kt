package com.example.user.jobche

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.joda.time.DateTime


@Parcelize
data class Filter(
    var title: String? = null,
    var city: String? = null,
    var dateStart: DateTime? = null,
    var dateEnd: DateTime? = null,
    var numWStart: String? = null,
    var pStart: String? = null
) : Parcelable
