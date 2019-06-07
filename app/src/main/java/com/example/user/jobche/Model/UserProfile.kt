package com.example.user.jobche.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var phoneNum: String = "",
    var profilePicture: String? = "",
    var dateOfBirth: DateOfBirth? = null,
    val reviews: ArrayList<Review> = ArrayList(),
    val averageReview: Double = 0.0
): Parcelable

@Parcelize
data class Review(
    val id: Long,
    val reviewGrade: ReviewGrade,
    val workId: Long,
    val comment: String,
    val reviewer:Reviewer
): Parcelable

enum class ReviewGrade {
    BAD, AVERAGE, GOOD, GREAT, PERFECT
}

@Parcelize
data class Reviewer(
    val id: Long,
    var firstName: String,
    var lastName: String,
    val comment: String,
    var profilePicture: String?
): Parcelable