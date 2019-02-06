package com.example.user.jobche.Model

data class Applications(val applications: ArrayList<Application>)

data class Application(val id: Int,
                       val accepted: Boolean,
                       val applicant: UserProfile,
                       val task: Task)