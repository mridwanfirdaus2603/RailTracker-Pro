package com.example.navigation

import kotlinx.serialization.Serializable

@Serializable
object Feed

@Serializable
object Map

@Serializable
object Schedule

@Serializable
object Stations

@Serializable
data class TrainDetail(val trainId: String)
