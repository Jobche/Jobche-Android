package com.example.user.jobche

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Tasks(val tasks: ArrayList<Task>)

@Parcelize
data class Task(
    private var title: String = "",
    private var city: String = "",
    private var description: String = "",
    private var payment: String = "",
    private var numberOfWorkers: String = "",
    val id: Long = 0,
    var dateTime: String = "",
    val creatorId: Long = 0,
    private var acceptedWorkersCount: Int = 0
) : Parcelable, BaseObservable() {

    var observedTitle: String
        @Bindable get() = title
        set(value) {
            title = value
            notifyPropertyChanged(BR.observedTitle)
        }

    var observedCity: String
        @Bindable get() = city
        set(value) {
            city = value
            notifyPropertyChanged(BR.observedCity)
        }

    var observedPayment: String
        @Bindable get() = payment
        set(value) {
            payment = value
            notifyPropertyChanged(BR.observedPayment)
        }

    var observedNumberOfWorkers: String
        @Bindable get() = numberOfWorkers
        set(value) {
            numberOfWorkers = value
            notifyPropertyChanged(BR.observedNumberOfWorkers)
        }

    var observedDescription: String
        @Bindable get() = description
        set(value) {
            description = value
            notifyPropertyChanged(BR.observedDescription)
        }

    var observedAcceptedWorkersCount: Int
        @Bindable get() = acceptedWorkersCount
        set(value) {
            acceptedWorkersCount = value
            notifyPropertyChanged(BR.observedAcceptedWorkersCount)
        }
}

