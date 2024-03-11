package com.example.activitylifecycle

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.activitylifecycle.ui.theme.ActivityLifecycleTheme

private const val TAG = "ACTIVITY_LIFECYCLE"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
        setContent {
            ActivityLifecycleTheme {
                val config = LocalConfiguration.current
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.size(
                    config.screenWidthDp.dp,
                    config.screenHeightDp.dp
                ), color = MaterialTheme.colorScheme.background) {
                    // Greeting("Android")
                    // Orientation(config)
                    // StyledText()
                    // HorizontalLayout()
                    // VerticalLayout()
                    // ConstrainedLayout()
                    // DynamicLayout(listOf("First item", "Second item", "Third item", "Fourth item"))
                    //ScrollableList()
                    AppLayout()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun BadScrollableList() {
    val items = (0 .. 10000).toList().map { "Item $it" }
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        for (item in items) {
            Text(item)
        }
    }
}

@Composable
fun ScrollableList() {
    val elems = (0 .. 10000).toList().map { "Item $it" }
    LazyColumn() {
        items(elems) {item ->
            Text(item)
        }
    }
}

@Composable
fun DynamicLayout(items: List<String>) {
    Column {
        for (item in items) {
            Text(item)
        }
    }
}

@Composable
fun ConstrainedLayout() {
    BoxWithConstraints {
        val box = this
        Column {
            if (box.maxHeight >= 400.dp) {
                Text(">= 400dp")
                Spacer(modifier = Modifier.size(10.dp))
            }
            Text("""
               minHeight: ${box.minHeight}
               maxHeight: ${box.maxHeight}
               minWidth: ${box.minWidth}
               maxWidth: ${box.maxWidth}
            """.trimIndent())
        }
    }
}

@Composable
fun VerticalLayout() {
    Column {
        Text("First item")
        Text("Second item")
        Text("Third item")
    }
}

@Composable
fun HorizontalLayout() {
    Row {
        Text("First item")
        Text("Second item")
        Text("Third item")
    }
}

@Composable
fun StyledText() {
    Text(
        "Hello World!",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun Orientation(config: Configuration) {
    Text(when(config.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> "Portrait"
        Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
        else -> "Unknown"
    }    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActivityLifecycleTheme {
        Greeting("Android")
    }
}