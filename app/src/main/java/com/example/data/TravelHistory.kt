package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel_history")
data class TravelHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val time: String,
    val route: String,
    val trainName: String
)
