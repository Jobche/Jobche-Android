package com.example.user.jobche

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Tasks(val tasks: ArrayList<Task>)

@Parcelize
data class Task(
    val id: Int = 0,
    var title: String = "",
    val location: Location = Location(),
    var payment: String = "",
    var numberOfWorkers: String = "",
    var description: String = "",
    var dateTime: String = "",
    val creatorId: Int = 0,
    var acceptedWorkersCount: Int = 0
) : Parcelable, BaseObservable() {


    var safeAcceptedWorkersCount: Int
        @Bindable get() = acceptedWorkersCount
        set(value) {
            acceptedWorkersCount = value
            notifyPropertyChanged(BR.safeAcceptedWorkersCount)
        }

}


@Parcelize
data class Location(
    val country: String = "България",
    var city: String = "",
    val neighborhood: String = ""
) : Parcelable
