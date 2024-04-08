package com.example.traveldiary.data.repositories

import com.example.traveldiary.data.database.Place
import com.example.traveldiary.data.database.PlacesDao

class PlacesRepository(
    private val placesDao: PlacesDao
) {
    val places = placesDao.getAll()

    suspend fun upsert(place: Place) = placesDao.upsert(place)

    suspend fun delete(place: Place) = placesDao.delete(place)
}