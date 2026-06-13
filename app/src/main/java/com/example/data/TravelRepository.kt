package com.example.data

import kotlinx.coroutines.flow.Flow

class TravelRepository(private val dao: TravelHistoryDao) {
    fun getAllHistory(): Flow<List<TravelHistory>> = dao.getAllHistory()

    suspend fun insertHistory(history: TravelHistory) {
        dao.insertHistory(history)
    }
}
