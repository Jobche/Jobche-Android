package com.example.user.jobche.Model

data class Tasks(var tasks: List<Task>)

data class Task(val id: Int,
                val title: String,
                val location: Location,
                val payment: Int,
                val numberOfWorkers: Int,
                val description: String,
                val dateTime: String)

data class Location(val country: String,
                    val city: String,
                    val neighborhood: String)
