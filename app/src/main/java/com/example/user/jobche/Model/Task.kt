package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Tasks(val tasks: ArrayList<Task>)

@Parcelize
data class Task(val id: Int,
                val title: String,
                val location: Location,
                val payment: Int,
                val numberOfWorkers: Int,
                val description: String,
                val dateTime: String,
                val creatorId: Int)  : Parcelable
@Parcelize
data class Location(val country: String,
                    val city: String,
                    val neighborhood: String): Parcelable
