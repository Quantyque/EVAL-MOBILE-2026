package com.example.eval_mobile_2026.presentation.locationdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eval_mobile_2026.audio.SoundManager
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LocationDetailScreen(
    locationId: Int,
    modifier: Modifier = Modifier,
    viewModel: LocationDetailViewModel = koinViewModel { parametersOf(locationId) },
    soundManager: SoundManager = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        soundManager.playDetailOpenSound()
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.error != null -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { viewModel.onAction(LocationDetailAction.Retry) }) {
                        Text("Réessayer")
                    }
                }
            }
            uiState.location != null -> {
                LocationDetailContent(
                    location = uiState.location!!,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun LocationDetailContent(
    location: com.example.eval_mobile_2026.domain.model.Location,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.headlineMedium
        )
        HorizontalDivider()
        Spacer(Modifier.height(4.dp))
        DetailRow(label = "Type", value = location.type)
        DetailRow(label = "Dimension", value = location.dimension)
        DetailRow(
            label = "Résidents",
            value = location.residentCount.toString()
        )
        DetailRow(label = "Créé le", value = location.created.substringBefore("T"))
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value.ifBlank { "—" },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
