package com.example.traveldiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.traveldiary.ui.TravelDiaryNavGraph
import com.example.traveldiary.ui.TravelDiaryRoute
import com.example.traveldiary.ui.composables.AppBar
import com.example.traveldiary.ui.theme.TravelDiaryTheme
import com.example.traveldiary.utils.LocationService
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    private lateinit var locationService: LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationService = get<LocationService>()

        setContent {
            TravelDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val backStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute by remember {
                        derivedStateOf {
                            TravelDiaryRoute.routes.find {
                                it.route == backStackEntry?.destination?.route
                            } ?: TravelDiaryRoute.Home
                        }
                    }

                    Scaffold(
                        topBar = { AppBar(navController, currentRoute) }
                    ) { contentPadding ->
                        TravelDiaryNavGraph(
                            navController,
                            modifier = Modifier.padding(contentPadding)
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        locationService.pauseLocationRequest()
    }

    override fun onResume() {
        super.onResume()
        locationService.resumeLocationRequest()
    }
}
