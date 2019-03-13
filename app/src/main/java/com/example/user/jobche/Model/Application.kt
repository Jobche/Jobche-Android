package com.example.user.jobche.Model

import com.example.user.jobche.Task

data class Applications(val applications: ArrayList<Application>)

data class Application(val id: Long,
                       val accepted: Boolean,
                       val applicant: UserProfile,
                       val task: Task
)