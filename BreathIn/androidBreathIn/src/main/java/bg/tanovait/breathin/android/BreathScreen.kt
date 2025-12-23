package bg.tanovait.breathin.android

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreathScreen(
    viewModel: BreathViewModel,
    onSettingsClick: () -> Unit,
    onCalendarClick: () -> Unit
) {
    LaunchedEffect(viewModel.isPlaying) {
        var lastTime = System.currentTimeMillis()
        while (viewModel.isPlaying) {
            val currentTime = System.currentTimeMillis()
            val deltaTime = currentTime - lastTime
            lastTime = currentTime
            viewModel.updateProgress(deltaTime)
            delay(16) // ~60 FPS
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Breathe") },
                actions = {
                    IconButton(onClick = onCalendarClick) {
                        Icon(Icons.Default.DateRange, "Calendar")
                    }
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, "Settings")
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Circle Animation
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircleVisualization(scale = viewModel.getSpiralScale())
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Phase Label
            Text(
                text = viewModel.phase.label,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Cycle Counter
            Card(
                modifier = Modifier.padding(horizontal = 32.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Current",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "${viewModel.currentCycle}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .height(48.dp)
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Remaining",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "${viewModel.targetCycles - viewModel.completedCycles}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Timer
            val currentDuration = when (viewModel.phase) {
                BreathPhase.Inhale -> viewModel.inhaleDuration
                BreathPhase.Hold1 -> viewModel.hold1Duration
                BreathPhase.Exhale -> viewModel.exhaleDuration
                BreathPhase.Hold2 -> viewModel.hold2Duration
            }
            val remainingTime = ((1 - viewModel.progress) * currentDuration).toInt() + 1
            Text(
                text = "${remainingTime}s",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { viewModel.togglePlay() },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = if (viewModel.isPlaying) Icons.Default.Refresh else Icons.Default.PlayArrow,
                        contentDescription = if (viewModel.isPlaying) "Pause" else "Play"
                    )
                }

                FloatingActionButton(
                    onClick = { viewModel.reset() },
                    containerColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Default.Refresh, "Reset")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Info
            Text(
                text = "Box Breathing: ${viewModel.inhaleDuration}-${viewModel.hold1Duration}-${viewModel.exhaleDuration}-${viewModel.hold2Duration}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Practice sessions: ${viewModel.completedDates.size} days",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CircleVisualization(scale: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val maxRadius = size.minDimension / 2
        val currentRadius = maxRadius * scale

        val gradient = Brush.radialGradient(
            colors = listOf(Color(0xFF8B5CF6), Color(0xFF3B82F6), Color.Transparent),
            center = center,
            radius = currentRadius
        )

        drawCircle(
            brush = gradient,
            radius = currentRadius,
            center = center
        )
    }
}
