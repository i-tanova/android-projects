package bg.tanovait.breathin.android

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.Breath) }
    val viewModel = remember { BreathViewModel() }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                Screen.Breath -> BreathScreen(
                    viewModel = viewModel,
                    onSettingsClick = { currentScreen = Screen.Settings },
                    onCalendarClick = { currentScreen = Screen.Calendar }
                )
                Screen.Settings -> SettingsScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.Breath }
                )
                Screen.Calendar -> CalendarScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.Breath }
                )
            }
        }
    }
}

enum class Screen {
    Breath, Settings, Calendar
}
