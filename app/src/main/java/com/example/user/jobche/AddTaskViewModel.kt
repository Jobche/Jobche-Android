package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Credentials
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskViewModel(val task: Task) : BaseObservable() {

    private lateinit var email: String

    private lateinit var password: String

    private var toastMsg: String = ""

    private var localDate: LocalDate? = null

    private var localTime: LocalTime? = null

    private var payment: String = ""

    private var numberOfWorkers: String = ""

    private var date: String = ""

    private var time: String = ""

    private val _dateEventLiveData = SingleLiveData<Any>()

    private val _timeEventLiveData = SingleLiveData<Any>()

    private val _addTaskEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

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


    fun getToastMsg(): String {
        return this.toastMsg
    }

    @Bindable
    fun getPayment(): String {
        return this.payment
    }

    fun setPayment(payment: String) {
        task.safePayment = payment.toInt()
        this.payment = payment
        notifyPropertyChanged(BR.payment)

    }

    @Bindable
    fun getNumberOfWorkers(): String {
        return this.numberOfWorkers
    }

    fun setNumberOfWorkers(numberOfWorkers: String) {
        task.safeNumberOfWorkers = numberOfWorkers.toInt()
        this.numberOfWorkers = numberOfWorkers
        notifyPropertyChanged(BR.numberOfWorkers)

    }

    @Bindable
    fun getDate(): String {
        return this.date
    }

    fun setDate(date: String) {
        this.date = date
        notifyPropertyChanged(BR.date)
    }

    fun formatDate(localDate: LocalDate): String {
        return String.format("%02d", localDate.dayOfMonth) + "." + String.format(
            "%02d",
            (localDate.monthOfYear)
        ) + "." + localDate.year
    }


    fun getLocalDate(): LocalDate {
        return this.localDate!!
    }

    fun setLocalDate(localDate: LocalDate) {
        setDate(formatDate(localDate))
        this.localDate = localDate
    }

    fun formatTime(localTime: LocalTime): String {
        return String.format("%02d", localTime.hourOfDay) + ":" + String.format("%02d", localTime.minuteOfHour)
    }

    fun getLocalTime(): LocalTime {
        return this.localTime!!
    }

    fun setLocalTime(localTime: LocalTime) {
        setTime(formatTime(localTime))
        this.localTime = localTime
    }

    @Bindable
    fun getTime(): String {
        return this.time
    }

    fun setTime(time: String) {
        this.time = time
        notifyPropertyChanged(BR.time)
    }

    fun getDateTime(localDate: LocalDate, localTime: LocalTime): LocalDateTime {
        return LocalDateTime(
            localDate.year,
            localDate.monthOfYear,
            localDate.dayOfMonth,
            localTime.hourOfDay,
            localTime.minuteOfHour
        )
    }

    val dateEventLiveData: LiveData<Any>
        get() = _dateEventLiveData

    val timeEventLiveData: LiveData<Any>
        get() = _timeEventLiveData

    val addTaskEventLiveData: LiveData<Any>
        get() = _addTaskEventLiveData

    val toastEventLiveData: LiveData<Any>
        get() = _toastEventLiveData

    fun onClickDate() {
        _dateEventLiveData.call()
    }

    fun onClickTime() {
        _timeEventLiveData.call()
    }

    fun onClickAddTask() {

        if (task.safeTitle == "") {
            toastMsg = "Моля въведете заглавие."
        } else if (task.safeLocation.safeCity == "") {
            toastMsg = "Моля въведете град, където ще се проведе работата."
        } else if (getPayment() == "" || getPayment().toInt() == 0) {
            toastMsg = "Моля въведете сума за заплащане."
        } else if (getNumberOfWorkers() == "" || getNumberOfWorkers().toInt() == 0) {
            toastMsg = "Моля въведете броя на нужните работници."
        } else if (getDate() == "" || getLocalDate().isBefore(LocalDate())) {
            toastMsg = "Моля въведете предстояща дата."
        } else if (getTime() == "") {
            toastMsg = "Моля въведете час за начало на работата."
        } else if (task.safeDescription == "") {
            toastMsg = "Моля въведете описание на работата."
        } else {
            toastMsg = ""
        }

        if (toastMsg != "") {
            _toastEventLiveData.call()
        } else {

            val paramObject = JsonObject()
            paramObject.addProperty("title", task.safeTitle)
            paramObject.addProperty("payment", task.safePayment)
            paramObject.addProperty("numberOfWorkers", task.safeNumberOfWorkers)
            paramObject.addProperty("description", task.safeDescription)
            paramObject.addProperty("dateTime", getDateTime(getLocalDate(), getLocalTime()).toString())
            paramObject.add(
                "location", Gson().toJsonTree(
                    Location(
                        task.safeLocation.safeCountry,
                        task.safeLocation.safeCity,
                        task.safeLocation.safeNeighborhood
                    )
                )
            )

            val authToken = Credentials.basic(getEmail(), getPassword())


            val call: Call<Task> = RetrofitClient().getApi()
                .createTask(authToken, paramObject)

            call.enqueue(object : Callback<Task> {
                override fun onFailure(call: Call<Task>, t: Throwable) {
                    Log.d("Add Task onFailure: ", t.message.toString())
                }

                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                    Log.d("Add Task onSuccess:", response.body().toString())
                    _addTaskEventLiveData.call()
                }

            })

        }

    }
}

