package com.example.attendence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject_table")
data class Subject(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var totalClasses: Int = 0,
    var attendedClasses: Int = 0
)