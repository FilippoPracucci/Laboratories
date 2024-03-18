package com.example.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        topBar = { AppBar(navController) }
                    ) {contentPadding ->
                        NavGraph(navController, Modifier.padding(contentPadding))
                    }
                }
            }
        }
    }
}

sealed class NavigationRoute(
    val route: String
) {
    data object Screen1 : NavigationRoute("Screen1")
    data object Screen2 : NavigationRoute("Screen2")
    data object Screen3 : NavigationRoute("Screen3")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination =  NavigationRoute.Screen1.route, modifier = modifier) {
        composable(NavigationRoute.Screen1.route) {
            Screen1(navController)
        }
        composable(NavigationRoute.Screen2.route) {
            Screen2(navController)
        }
        composable(NavigationRoute.Screen3.route) {
            Screen3(navController)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    NavigationRoute.Screen1.route

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationTheme {
        Greeting("Android")
    }
}