package com.example.traveldiary.ui.screens.addtravel

import androidx.lifecycle.ViewModel
import com.example.traveldiary.data.database.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AddTravelState(
    val destination: String = "",
    val date: String = "",
    val description: String = ""
) {
    val canSubmit get() = destination.isNotBlank() && date.isNotBlank() && description.isNotBlank()

    fun toPlace() = Place(
        name = destination,
        date = date,
        description = description
    )
}

interface AddTravelActions {
    fun setDestination(title: String)
    fun setDate(date: String)
    fun setDescription(description: String)

}

class AddTravelViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddTravelState())
    val state = _state.asStateFlow()

    val actions = object : AddTravelActions {
        override fun setDestination(title: String) =
            _state.update { it.copy(destination = title) }

        override fun setDate(date: String) =
            _state.update { it.copy(date = date) }

        override fun setDescription(description: String) =
            _state.update { it.copy(description = description) }

    }
}
