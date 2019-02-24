package com.example.user.jobche.Model


data class UserProfile(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNum: String,
    val dateOfBirth: DateOfBirth,
    val reviews: ArrayList<Review>
)

data class Review(
    val id: Int,
    val reviewGrade: ReviewGrade,
    val workId: Int
)

enum class ReviewGrade {
    BAD, AVERAGE, GOOD, GREAT, PERFECT
}