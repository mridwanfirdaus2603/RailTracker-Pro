package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelHistoryDao {
    @Query("SELECT * FROM travel_history ORDER BY id DESC")
    fun getAllHistory(): Flow<List<TravelHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: TravelHistory)

    @Query("DELETE FROM travel_history")
    suspend fun clearHistory()
}
