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

class AddTask: BaseObservable() {

    var title: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    var city: String = ""
        @Bindable get() = field
        set(value) {
            field = value
//            errorTask.errorCity = ""
            notifyPropertyChanged(BR.city)
        }

    var payment: String = ""
        @Bindable get() = field
        set(value) {
            field = value
//            errorTask.errorPayment = ""
            notifyPropertyChanged(BR.payment)
        }

    var numberOfWorkers: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.numberOfWorkers)
        }

    var dateTime: String = ""

    var description: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.description)
        }


}


@Parcelize
data class Location(
    val country: String = "България",
    var city: String = "",
    val neighborhood: String = ""
) : Parcelable, BaseObservable()
