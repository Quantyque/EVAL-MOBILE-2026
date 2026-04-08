package com.example.eval_mobile_2026.presentation.desktop

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eval_mobile_2026.presentation.locationdetail.LocationDetailScreen
import com.example.eval_mobile_2026.presentation.locationlist.LocationListScreen

@Composable
fun DesktopScreen(modifier: Modifier = Modifier) {
    var selectedLocationId by remember { mutableStateOf<Int?>(null) }

    Row(modifier = modifier.fillMaxSize()) {
        // List panel (40% width)
        Surface(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight(),
            tonalElevation = 1.dp
        ) {
            LocationListScreen(
                onLocationClick = { id -> selectedLocationId = id }
            )
        }

        // Vertical divider
        Surface(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight(),
            color = MaterialTheme.colorScheme.outlineVariant
        ) {}

        // Detail panel (60% width)
        Surface(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
        ) {
            val locationId = selectedLocationId
            if (locationId != null) {
                // Use a unique key so the screen resets when a different location is selected
                androidx.compose.runtime.key(locationId) {
                    LocationDetailScreen(locationId = locationId)
                }
            } else {
                EmptyDetailPanel(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun EmptyDetailPanel(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Select a location from the list",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
