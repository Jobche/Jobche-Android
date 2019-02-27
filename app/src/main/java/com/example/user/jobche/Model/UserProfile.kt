package com.example.user.jobche.Model


data class UserProfile(
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var phoneNum: String = "",
    var dateOfBirth: DateOfBirth? = null,
    val reviews: ArrayList<Review> = ArrayList()
)

data class Review(
    val id: Int,
    val reviewGrade: ReviewGrade,
    val workId: Int
)

enum class ReviewGrade {
    BAD, AVERAGE, GOOD, GREAT, PERFECT
}