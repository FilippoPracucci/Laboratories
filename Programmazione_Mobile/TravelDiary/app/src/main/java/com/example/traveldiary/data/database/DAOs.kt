package com.example.traveldiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao {
    @Query("SELECT * FROM place ORDER BY name ASC")
    fun getAll(): Flow<List<Place>>

    @Upsert
    suspend fun upsert(place: Place)

    @Delete
    suspend fun delete(place: Place)
}