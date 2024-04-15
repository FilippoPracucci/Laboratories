package com.example.gps

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.gps.ui.theme.GPSTheme
import com.example.gps.utils.LocationService
import com.example.gps.utils.PermissionStatus
import com.example.gps.utils.rememberPermission

class MainActivity : ComponentActivity() {
    // Fatta direttamente nella MainActivity e non Compsable perché vogliamo interrompere la richiesta quando in background
    private lateinit var locationService: LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationService = LocationService(this)

        setContent {
            GPSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val snackbarHostState = remember { SnackbarHostState() }

                    var showPermissionDeniedAlert by remember { mutableStateOf(false) }
                    var showPermissionPermanentlyDeniedSnackbar by remember { mutableStateOf(false) }
                    var showLocationDisabledAlert by remember { mutableStateOf(false) }

                    val locationPermission = rememberPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) { status ->
                        when (status) {
                            PermissionStatus.Unknown -> {}
                            PermissionStatus.Granted -> locationService.requestCurrentLocation()
                            PermissionStatus.Denied ->
                                // Explain why the permission is required
                                showPermissionDeniedAlert = true
                            PermissionStatus.PermanentlyDenied ->
                                // Go to settings manually
                                showPermissionPermanentlyDeniedSnackbar = true

                        }
                    }

                    fun requestLocation() {
                        if (locationPermission.status.isGranted) {
                            locationService.requestCurrentLocation()
                        } else {
                            locationPermission.launchPermissionRequest()
                        }
                    }

                    if (locationService.isLocationEnabled == false) {
                        LaunchedEffect(locationService.isLocationEnabled) {
                            showLocationDisabledAlert = locationService.isLocationEnabled == false
                        }
                    }

                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                    ) { contentPadding ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(contentPadding)
                                .fillMaxSize()
                        ) {
                            Button(onClick = ::requestLocation) {
                                Text("Get current location")
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Latitude: ${locationService.coordinates?.latitude ?: "-"}")
                            Text("Longitude: ${locationService.coordinates?.longitude ?: "-"}")
                        }
                    }

                    if (showLocationDisabledAlert) {
                        AlertDialog(
                            title = { Text("Location disabled") },
                            text = { Text("Location must be enabled to get your current location in the app.") },
                            confirmButton = {
                                TextButton(onClick = {
                                    locationService.openLocationSettings()
                                    showLocationDisabledAlert = false
                                }) {
                                    Text("Enable")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showLocationDisabledAlert = false }) {
                                    Text("Dismiss")
                                }
                            },
                            onDismissRequest = {
                                showLocationDisabledAlert = false
                            }
                        )
                    }

                    if (showPermissionDeniedAlert) {
                        AlertDialog(
                            title = { Text("Location permission denied") },
                            text = { Text("Location permission is required to get your current location in the app.") },
                            confirmButton = {
                                TextButton(onClick = {
                                    locationPermission.launchPermissionRequest()
                                    showPermissionDeniedAlert = false
                                }) {
                                    Text("Grant")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showPermissionDeniedAlert = false }) {
                                    Text("Dismiss")
                                }
                            },
                            onDismissRequest = {
                                showPermissionDeniedAlert = false
                            }
                        )
                    }

                    val ctx = LocalContext.current
                    if (showPermissionPermanentlyDeniedSnackbar) {
                        LaunchedEffect(snackbarHostState) {
                            val res = snackbarHostState.showSnackbar(
                                "Location permission is denied",
                                "Go to settings",
                                duration = SnackbarDuration.Long
                            )
                            if (res == SnackbarResult.ActionPerformed) {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                    data = Uri.fromParts("package", ctx.packageName, null)
                                }
                                if (intent.resolveActivity(ctx.packageManager) != null) {
                                    ctx.startActivity(intent)
                                }
                            }
                            showPermissionPermanentlyDeniedSnackbar = false
                        }
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
