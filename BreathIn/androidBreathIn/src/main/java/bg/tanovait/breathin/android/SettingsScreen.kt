package bg.tanovait.breathin.android

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: BreathViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            SettingSlider(
                label = "Breathe In",
                value = viewModel.inhaleDuration,
                onValueChange = { viewModel.inhaleDuration = it }
            )

            SettingSlider(
                label = "Hold (after inhale)",
                value = viewModel.hold1Duration,
                onValueChange = { viewModel.hold1Duration = it }
            )

            SettingSlider(
                label = "Breathe Out",
                value = viewModel.exhaleDuration,
                onValueChange = { viewModel.exhaleDuration = it }
            )

            SettingSlider(
                label = "Hold (after exhale)",
                value = viewModel.hold2Duration,
                onValueChange = { viewModel.hold2Duration = it }
            )
        }
    }
}

@Composable
fun SettingSlider(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column {
        Text(
            text = "$label (${value}s)",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 2f..10f,
            steps = 7
        )
    }
}