package com.example.traveldiary.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.traveldiary.ui.TravelDiaryRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    currentRoute: TravelDiaryRoute
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                currentRoute.title,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Outlined.ArrowBack, "Back button")
                }
            }
        },
        actions = {
          if (currentRoute.route == TravelDiaryRoute.Home.route) {
              IconButton(onClick = { /*TODO*/ }) {
                  Icon(Icons.Outlined.Search, contentDescription = "Search")
              }
          }
          if (currentRoute.route != TravelDiaryRoute.Settings.route) {
              IconButton(onClick = { navController.navigate(TravelDiaryRoute.Settings.route) }) {
                  Icon(Icons.Outlined.Settings, contentDescription = "Settings")
              }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}