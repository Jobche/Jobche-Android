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

class AddTaskViewModel(val task: AddTask, private val email: String, private val password: String) : BaseObservable() {

    val errorMsg: String = "Моля попълнете полето."

    var onClicked: Boolean = false
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.onClicked)
        }


    var localDate: LocalDate? = null
        set(value) {
            date = (formatDate(value!!))
            field = value
        }
    var localTime: LocalTime? = null
        set(value) {
            time = (formatTime(value!!))
            field = value
        }
    var date: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    var time: String = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }
    private val _dateEventLiveData = SingleLiveData<Any>()

    private val _timeEventLiveData = SingleLiveData<Any>()

    private val _addTaskEventLiveData = SingleLiveData<Any>()

    private val _toastEventLiveData = SingleLiveData<Any>()

    private fun formatDate(localDate: LocalDate): String {
        return String.format("%02d", localDate.dayOfMonth) + "." + String.format(
            "%02d",
            (localDate.monthOfYear)
        ) + "." + localDate.year
    }

    private fun formatTime(localTime: LocalTime): String {
        return String.format("%02d", localTime.hourOfDay) + ":" + String.format("%02d", localTime.minuteOfHour)
    }

    private fun getDateTime(localDate: LocalDate, localTime: LocalTime): LocalDateTime {
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

        onClicked = true
        if (task.title.isNotEmpty() && task.city.isNotEmpty() && task.payment.isNotEmpty() && task.numberOfWorkers.isNotEmpty()
            && task.description.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {

            val paramObject = JsonObject()
            paramObject.addProperty("title", task.title)
            paramObject.addProperty("payment", task.payment.toInt())
            paramObject.addProperty("numberOfWorkers", task.numberOfWorkers.toInt())
            paramObject.addProperty("description", task.description)
            paramObject.addProperty("dateTime", getDateTime(localDate!!, localTime!!).toString())
            paramObject.add(
                "location", Gson().toJsonTree(
                    Location(
                        "България",
                        task.city,
                        ""
                    )
                )
            )

            val call: Call<Task> = RetrofitClient().api
                .createTask(Credentials.basic(email, password), paramObject)

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

