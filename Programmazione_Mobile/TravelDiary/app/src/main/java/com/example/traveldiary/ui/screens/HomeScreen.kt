package com.example.traveldiary.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.traveldiary.AppBar

@Composable
fun TravelDiary() {
    Column {
        Scaffold(
            topBar = { AppBar() }
        ) { contentPadding ->
            Text("TravelDiary", Modifier.padding(contentPadding))
        }
        ScrollableList()
    }
}

@Composable
fun ScrollableList() {
    val elems = (0..20).toList().map { "Item n°$it" }
    
    LazyColumn {
        items(elems) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
            ) {
                Text(
                    it,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}