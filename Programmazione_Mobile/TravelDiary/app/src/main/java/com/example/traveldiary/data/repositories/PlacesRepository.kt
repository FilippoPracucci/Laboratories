package com.example.traveldiary.data.repositories

import android.content.ContentResolver
import android.net.Uri
import com.example.traveldiary.data.database.Place
import com.example.traveldiary.data.database.PlacesDao
import com.example.traveldiary.utils.saveImageToStorage
import kotlinx.coroutines.flow.Flow

class PlacesRepository(
    private val placesDao: PlacesDao,
    private val contentResolver: ContentResolver
) {
    val places: Flow<List<Place>> = placesDao.getAll()

    suspend fun upsert(place: Place) {
        if (place.imageUri?.isNotEmpty() == true) {
            val imageUri = saveImageToStorage(
                Uri.parse(place.imageUri),
                contentResolver,
            "TravelDiary_Place${place.name}"
            )
            placesDao.upsert(place.copy(imageUri = imageUri.toString()))
        } else {
            placesDao.upsert(place)
        }

    }

    suspend fun delete(place: Place) = placesDao.delete(place)
}