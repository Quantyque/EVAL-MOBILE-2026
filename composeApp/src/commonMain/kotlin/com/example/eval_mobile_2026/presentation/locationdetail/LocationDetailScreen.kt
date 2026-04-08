package com.example.eval_mobile_2026.presentation.locationdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eval_mobile_2026.audio.SoundManager
import com.example.eval_mobile_2026.domain.model.Location
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

/**
 * Displays the detail view for a single location.
 *
 * - When [onBack] is non-null (mobile), a [TopAppBar] with a back arrow is rendered and the
 *   location name is shown there. When [onBack] is null (desktop), the name appears in the
 *   content body instead, avoiding duplication.
 * - The ViewModel is keyed by [locationId] so that each location gets its own instance. This
 *   is critical on Desktop where the Window-level ViewModelStore would otherwise reuse the same
 *   instance when the user selects a different location in the list panel.
 * - A sound is played once on composition via [LaunchedEffect].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailScreen(
    locationId: Int,
    onBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    // key ensures a distinct ViewModel per location, preventing stale state when switching items
    viewModel: LocationDetailViewModel = koinViewModel(key = locationId.toString()) { parametersOf(locationId) },
    soundManager: SoundManager = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        soundManager.playDetailOpenSound()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (onBack != null) {
                TopAppBar(
                    title = { Text(uiState.location?.name ?: "") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
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
                            Text("Retry")
                        }
                    }
                }
                uiState.location != null -> {
                    LocationDetailContent(
                        location = uiState.location!!,
                        residentNames = uiState.residentNames,
                        isLoadingResidents = uiState.isLoadingResidents,
                        // Title is already shown in the TopAppBar on mobile; avoid duplication
                        showTitle = onBack == null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationDetailContent(
    location: Location,
    residentNames: List<String>,
    isLoadingResidents: Boolean,
    showTitle: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showTitle) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineMedium
            )
            HorizontalDivider()
            Spacer(Modifier.height(4.dp))
        }
        DetailRow(label = "Type", value = location.type)
        DetailRow(label = "Dimension", value = location.dimension)
        ResidentsSection(
            totalCount = location.residents.size,
            names = residentNames,
            isLoading = isLoadingResidents
        )
        DetailRow(label = "Created", value = location.created.substringBefore("T"))
    }
}

@Composable
private fun ResidentsSection(
    totalCount: Int,
    names: List<String>,
    isLoading: Boolean
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Residents ($totalCount)",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        when {
            totalCount == 0 -> Text(
                text = "No residents",
                style = MaterialTheme.typography.bodyLarge
            )
            isLoading -> CircularProgressIndicator(strokeWidth = 2.dp)
            names.isNotEmpty() -> {
                names.forEach { name ->
                    Text(text = "• $name", style = MaterialTheme.typography.bodyMedium)
                }
                if (totalCount > names.size) {
                    Text(
                        text = "+ ${totalCount - names.size} more",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
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
