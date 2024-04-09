package com.example.traveldiary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Place::class], version = 1)
abstract class TravelDiaryDatabase : RoomDatabase() {
    abstract fun placesDAO(): PlacesDao
}
