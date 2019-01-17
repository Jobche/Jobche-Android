package com.example.user.jobche

import android.text.TextUtils.substring
import com.example.user.jobche.Model.Task

class OpenedTaskViewModel(val task: Task) {

    private val date:String = substring(task.dateTime, 0, 5)

    private val time:String = substring(task.dateTime, 5, task.dateTime.length)

    private val payment:String = task.payment.toString()

    private val numberOfWorkers:String = task.numberOfWorkers.toString()

    fun getDate(): String {
        return this.date
    }

    fun getTime(): String {
        return this.time
    }

    fun getPayment(): String {
        return this.payment
    }

    fun getNumberOfWorkers(): String {
        return this.numberOfWorkers
    }


}