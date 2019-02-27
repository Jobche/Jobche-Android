package com.example.user.jobche.Model

import com.example.user.jobche.Task
import org.joda.time.DateTime

data class Work(val createdAt: String,
                val id: Long,
                val status: String,
                val task: Task,
                val workers: ArrayList<UserProfile>)

enum class Status {
    IN_PROGRESS, ENDED
}