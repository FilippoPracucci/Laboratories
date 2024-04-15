package com.example.camera

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.camera.ui.theme.CameraTheme
import com.example.camera.utils.rememberCameraLauncher
import com.example.camera.utils.rememberPermission
import com.example.camera.utils.saveImageToStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val ctx = LocalContext.current

                    val cameraLauncher = rememberCameraLauncher()

                    val cameraPermission = rememberPermission(Manifest.permission.CAMERA) { status ->
                        if (status.isGranted) {
                            cameraLauncher.captureImage()
                        } else {
                            Toast.makeText(ctx, "Permission denied", Toast.LENGTH_SHORT).show()                        }
                    }

                    fun takePicture() =
                        if (cameraPermission.status.isGranted) {
                            cameraLauncher.captureImage()
                        } else {
                            cameraPermission.launchPermissionRequest()
                        }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Button(onClick = { takePicture() }) {
                            Text("Take a Picture")
                        }
                    }

                    if (cameraLauncher.capturedImageUri.path?.isNotEmpty() == true) {
                        AsyncImage(
                            ImageRequest.Builder(ctx)
                                .data(cameraLauncher.capturedImageUri)
                                .crossfade(true)
                                .build(),
                            "Capture image"
                        )
                    }
                }
            }
        }
    }
}
