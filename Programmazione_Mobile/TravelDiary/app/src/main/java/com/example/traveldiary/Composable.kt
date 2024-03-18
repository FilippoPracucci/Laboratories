package com.example.traveldiary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {

    TopAppBar(
        title = { Text("TravelDiary") },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.Search, "Search")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.Settings, "Settings")
            }
        }
    )
}