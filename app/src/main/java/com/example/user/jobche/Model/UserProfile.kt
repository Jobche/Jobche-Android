package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    var id: Int = 0,
    var firstName: String = "",
    var lastName: String = "",
    var phoneNum: String = "",
    var dateOfBirth: DateOfBirth? = null,
    val reviews: ArrayList<Review> = ArrayList()
): Parcelable

@Parcelize
data class Review(
    val id: Int,
    val reviewGrade: ReviewGrade,
    val workId: Int
): Parcelable

enum class ReviewGrade {
    BAD, AVERAGE, GOOD, GREAT, PERFECT
}