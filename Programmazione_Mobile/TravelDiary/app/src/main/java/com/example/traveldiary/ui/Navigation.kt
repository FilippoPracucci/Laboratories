package com.example.traveldiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.traveldiary.ui.screens.AddTravelScreen
import com.example.traveldiary.ui.screens.HomeScreen
import com.example.traveldiary.ui.screens.SettingsScreen
import com.example.traveldiary.ui.screens.SettingsViewModel
import com.example.traveldiary.ui.screens.TravelDetailsScreen
import org.koin.androidx.compose.koinViewModel

sealed class TravelDiaryRoute (
    val route: String,
    val title: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : TravelDiaryRoute("travels", "TravelDiary")
    data object TravelDetails : TravelDiaryRoute(
        "travels/{travelId}",
        "Travel Details",
        listOf(navArgument("travelId") { type = NavType.StringType})
    ) {
        fun buildRoute(travelId: String) = "travels/{$travelId}"
    }
    data object AddTravel : TravelDiaryRoute("travels/add", "Add Travel")
    data object Settings : TravelDiaryRoute("settings", "Settings")

    companion object {
        val routes = setOf(Home, TravelDetails, AddTravel, Settings)
    }
}

@Composable
fun TravelDiaryNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TravelDiaryRoute.Home.route,
        modifier = modifier
    ) {
        with(TravelDiaryRoute.Home) {
            composable(route) {
                HomeScreen(navController)
            }
        }
        with(TravelDiaryRoute.TravelDetails) {
            composable(route) { backStackEntry ->
                TravelDetailsScreen(backStackEntry.arguments?.getString("travelId") ?: "")
            }
        }
        with(TravelDiaryRoute.AddTravel) {
            composable(route, arguments) {
                AddTravelScreen(navController)
            }
        }
        with(TravelDiaryRoute.Settings) {
            composable(route) {
                val settingsVm = koinViewModel<SettingsViewModel>()
                SettingsScreen(settingsVm.state, settingsVm::setUsername)
            }
        }
    }
}