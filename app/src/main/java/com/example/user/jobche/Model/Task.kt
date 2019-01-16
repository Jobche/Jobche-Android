package com.example.user.jobche.Model

import java.text.DateFormat
import java.time.LocalDateTime
import java.util.*

data class Task(val id: Int,
                val title: String,
                val location: Location,
                val payment: Int,
                val numOfWorkers: Int,
                val description: String,
                val dateTime: String)

data class Location(val county: String,
                    val city: String,
                    val neighborhood: String)
