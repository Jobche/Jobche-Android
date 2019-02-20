package com.example.user.jobche

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

data class Tasks(val tasks: ArrayList<Task>)

@Parcelize
data class Task(
    private val id: Int = 0,
    private var title: String = "",
    private val location: Location = Location(),
    private var payment: Int = 0,
    private var numberOfWorkers: Int = 0,
    private var description: String = "",
    private var dateTime: String = "",
    private var creatorId: Int = 0,
    private var acceptedWorkersCount: Int = 0
) : Parcelable, BaseObservable() {

    val safeId: Int
        get() = id

    var safeTitle: String
        get() = title
        set(value) {
            title = value
        }

    val safeLocation: Location
        get() = location


    var safePayment: Int
        get() = payment
        set(value) {
            payment = value
        }

    var safeNumberOfWorkers: Int
        get() = numberOfWorkers
        set(value) {
            numberOfWorkers = value
        }

    var safeDescription: String
        get() = description
        set(value) {
            description = value
        }

    var safeDateTime: String
        get() = dateTime
        set(value) {
            dateTime = value
        }

    val safeCreatorId: Int
        get() = creatorId

    var safeAcceptedWorkersCount: Int
        @Bindable get() = acceptedWorkersCount
        set(value) {
            acceptedWorkersCount = value
            notifyPropertyChanged(BR.safeAcceptedWorkersCount)
        }

}


@Parcelize
data class Location(
    private val country: String = "България",
    private var city: String = "",
    private val neighborhood: String = ""
) : Parcelable, BaseObservable() {

    val safeCountry: String
        get() = country

    var safeCity: String
        get() = city
        set(value) {
            city = value
        }

    val safeNeighborhood: String
        get() = neighborhood

}
