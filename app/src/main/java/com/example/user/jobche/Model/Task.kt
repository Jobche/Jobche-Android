package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Tasks(var tasks: List<Task>)

@Parcelize
data class Task(val title: String,
                val location: Location,
                val payment: Int,
                val numberOfWorkers: Int,
                val description: String,
                val dateTime: String)  : Parcelable
@Parcelize
data class Location(val country: String,
                    val city: String,
                    val neighborhood: String): Parcelable
