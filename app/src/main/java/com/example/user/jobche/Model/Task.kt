package com.example.user.jobche.Model

import java.text.DateFormat
import java.util.*

data class Task(private var title: String,
                private var payment: Int,
                private var numOfWorkers: Int,
                private var description: String,
                private var dateTime: Date)