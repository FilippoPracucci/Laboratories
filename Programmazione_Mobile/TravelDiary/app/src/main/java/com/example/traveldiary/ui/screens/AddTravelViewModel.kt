package com.example.traveldiary.ui.screens

import androidx.lifecycle.ViewModel

class AddTravelViewModel(

) : ViewModel() {

}

interface AddTravelActions {
    fun setDestination(title: String)

    fun setDate(date: String)

    fun setDescription(description: String)

}