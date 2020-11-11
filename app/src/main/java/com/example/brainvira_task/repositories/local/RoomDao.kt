package com.example.brainvira_task.repositories.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.brainvira_task.model.SearchItemDetailsResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Insert(onConflict = REPLACE)
    suspend fun addSearchItemDetailsResponse(trResponse: SearchItemDetailsResponse)

    @Query("SELECT * FROM SearchItemDetailsResponse")
    fun getSearchItemDetailsResponse(): Flow<SearchItemDetailsResponse>
}