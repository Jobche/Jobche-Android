package com.example.user.jobche.Model

data class Applications(var applications: List<Application>)

data class Application(val id: Int,
                       val applicantId: Int,
                       val taskId: Int,
                       val accepted: Boolean)