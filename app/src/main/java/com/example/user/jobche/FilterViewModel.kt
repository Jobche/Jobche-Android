package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.user.jobche.Model.Filter
import org.joda.time.DateTime

class FilterViewModel : BaseObservable() {

    private lateinit var email: String

    private lateinit var password: String

    private var title: String? = null

    private var city: String? = null

    private var payment: String? = null

    private var numOfWorkers: String? = null

    private var formattedDateStart: String = ""

    private var formattedDateEnd: String = ""

    private var dateStart: String? = null

    private var dateEnd: String? = null

    private val _beginDateEventLiveData = SingleLiveData<Any>()

    private val _endDateEventLiveData = SingleLiveData<Any>()

    private val _searchTasksEventLiveData = SingleLiveData<Any>()

    val beginDateEventLiveData: LiveData<Any>
        get() = _beginDateEventLiveData

    val endDateEventLiveData: LiveData<Any>
        get() = _endDateEventLiveData

    val searchTasksEventLiveData: LiveData<Any>
        get() = _searchTasksEventLiveData

    fun getEmail(): String {
        return this.email
    }

    fun setEmail(email: String) {
        this.email = email
    }


    fun getPassword(): String {
        return this.password
    }

    fun setPassword(password: String) {
        this.password = password
    }


    @Bindable
    fun getCity(): String? {
        return this.city
    }

    fun setCity(city: String) {
        this.city = city
        notifyPropertyChanged(BR.city)
    }

    @Bindable
    fun getTitle(): String? {
        return this.title
    }

    fun setTitle(title: String) {
        this.title = title
        notifyPropertyChanged(BR.title)
    }

    @Bindable
    fun getPayment(): String? {
        return this.payment
    }

    fun setPayment(payment: String) {
        this.payment = payment
        notifyPropertyChanged(BR.payment)
    }

    @Bindable
    fun getNumOfWorkers(): String? {
        return this.numOfWorkers
    }

    fun setNumOfWorkers(numOfWorkers: String) {
        this.numOfWorkers = numOfWorkers
        notifyPropertyChanged(BR.numOfWorkers)
    }

    @Bindable
    fun getFormattedDateStart(): String {
        return this.formattedDateStart
    }

    fun setFormattedDateStart(dateTime: DateTime) {
        this.formattedDateStart = formatDate(dateTime)
        notifyPropertyChanged(BR.formattedDateStart)

    }

    @Bindable
    fun getFormattedDateEnd(): String {
        return this.formattedDateEnd
    }

    fun setFormattedDateEnd(dateTime: DateTime) {
        this.formattedDateEnd = formatDate(dateTime)
        notifyPropertyChanged(BR.formattedDateEnd)

    }

    fun formatDate(dateTime: DateTime): String {
        return String.format("%02d", dateTime.dayOfMonth) + "." +
                String.format("%02d", dateTime.monthOfYear) + "." +
                dateTime.year.toString()
    }

    fun getDateStart(): String? {
        return this.dateStart
    }

    fun setDateStart(dateStart: String) {
        this.dateStart = dateStart
    }

    fun getDateEnd(): String? {
        return this.dateEnd
    }

    fun setDateEnd(dateEnd: String) {
        this.dateEnd = dateEnd
    }

    fun onBeginDate() {
        _beginDateEventLiveData.call()
    }

    fun onEndDate() {
        _endDateEventLiveData.call()
    }

    fun getFilter(): Filter {
        return Filter(
            getTitle(), getCity(), getDateStart(), getDateEnd(),
            getNumOfWorkers()?.toInt(), getPayment()?.toInt()
        )
    }

    fun onClickSearch() {
        _searchTasksEventLiveData.call()
    }

}
