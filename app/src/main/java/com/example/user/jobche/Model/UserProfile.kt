package com.example.user.jobche.Model

data class UserProfile(var id:Int = 0,
                       var firstName: String? = "",
                       var lastName: String? = "",
                       var dateOfBirth: DateOfBirth? = null)