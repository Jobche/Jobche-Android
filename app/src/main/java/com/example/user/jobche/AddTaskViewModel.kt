package com.example.user.jobche

import android.arch.lifecycle.LiveData
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.google.gson.JsonObject
import okhttp3.Credentials
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class AddTaskViewModel(val task: Task, private val email: String, private val password: String) : BaseObservable() {

    val errorMsg: String = "Моля попълнете полето."

    @Bindable
    var onClicked: Boolean = false
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

    @Bindable
    var date: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @Bindable
    var time: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }
    val dateEventLiveData = SingleLiveData<Any>()

    val timeEventLiveData = SingleLiveData<Any>()

    val addTaskEventLiveData = SingleLiveData<Any>()

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

    fun onClickDate() {
        dateEventLiveData.call()
    }

    fun onClickTime() {
        timeEventLiveData.call()
    }

    fun onClickAddTask() {

        onClicked = true

        if (task.observedTitle.isNotEmpty() && task.observedCity.isNotEmpty() && task.observedPayment.isNotEmpty()
            && task.observedNumberOfWorkers.isNotEmpty() && task.observedDescription.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {

            val paramObject = JsonObject()
            paramObject.addProperty("title", task.observedTitle)
            paramObject.addProperty("city", task.observedCity)
            paramObject.addProperty("payment", task.observedPayment.toInt())
            paramObject.addProperty("numberOfWorkers", task.observedNumberOfWorkers.toInt())
            paramObject.addProperty("description", task.observedDescription)
            paramObject.addProperty("dateTime", getDateTime(localDate!!, localTime!!).toString())


            val call: Call<Task> = RetrofitClient().api
                .createTask(Credentials.basic(email, password), paramObject)

            call.enqueue(object : Callback<Task> {
                override fun onFailure(call: Call<Task>, t: Throwable) {
                    Log.d("Add Task onFailure: ", t.message.toString())
                }

                override fun onResponse(call: Call<Task>, response: Response<Task>) {
                    Log.d("Add Task onSuccess:", response.body().toString())
                    addTaskEventLiveData.call()
                }

            })

        }
    }
}

