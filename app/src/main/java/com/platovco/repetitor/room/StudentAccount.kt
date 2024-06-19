package com.platovco.repetitor.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_accounts")
data class StudentAccount(
    @PrimaryKey val id: Long,
    val photo: String,
    val name: String,
    val surname: String,
    val city: String,
    val birthday: String,
    val direction: String,
    val budget: Long,
    val reason: String
)
