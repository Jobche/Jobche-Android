package com.example.user.jobche.Model

import org.joda.time.LocalDate

data class UserProfile(val id:Int,
                       val firstName: String,
                       val lastName: String,
                       val dateOfBirth: LocalDate
)