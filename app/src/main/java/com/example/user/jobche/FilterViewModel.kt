package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import org.joda.time.DateTime

class FilterViewModel(val filter: Filter) : BaseObservable() {

    var dateStart: DateTime? = null
        set(value) {
            formattedDateStart = formatDate(value)
            filter.dateStart = value
        }

    var dateEnd: DateTime? = null
        set(value) {
            formattedDateEnd = (formatDate(value))
            filter.dateEnd = value
        }

    var formattedDateStart: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.formattedDateStart)
        }

    var formattedDateEnd: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.formattedDateEnd)
        }


    val beginDateEventLiveData = SingleLiveData<Any>()

    val endDateEventLiveData = SingleLiveData<Any>()

    val searchTasksEventLiveData = SingleLiveData<Any>()

//    val beginDateEventLiveData: LiveData<Any>
//        get() = _beginDateEventLiveData
//
//    val endDateEventLiveData: LiveData<Any>
//        get() = _endDateEventLiveData
//
//    val searchTasksEventLiveData: LiveData<Any>
//        get() = _searchTasksEventLiveData

    fun formatDate(dateTime: DateTime?): String {

        return if (dateTime == null) {
            ""
        } else {
            String.format("%02d", dateTime.dayOfMonth) + "." +
                    String.format("%02d", dateTime.monthOfYear) + "." +
                    dateTime.year.toString()
        }
    }

    fun onBeginDate() {
        beginDateEventLiveData.call()
    }

    fun onEndDate() {
        endDateEventLiveData.call()
    }

    fun onClickSearch() {
        searchTasksEventLiveData.call()
    }

}
