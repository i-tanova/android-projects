package bg.tanovait.breathin.android

import androidx.compose.runtime.*
import kotlinx.datetime.*

class BreathViewModel {
    var isPlaying by mutableStateOf(false)
    var phase by mutableStateOf(BreathPhase.Inhale)
    var progress by mutableStateOf(0f)
    var sessionStarted by mutableStateOf(false)

    var inhaleDuration by mutableStateOf(4)
    var hold1Duration by mutableStateOf(4)
    var exhaleDuration by mutableStateOf(4)
    var hold2Duration by mutableStateOf(4)

    var completedDates by mutableStateOf(setOf<String>())

    fun togglePlay() {
        isPlaying = !isPlaying
        if (isPlaying && !sessionStarted) {
            sessionStarted = true
            markTodayComplete()
        }
    }

    fun reset() {
        isPlaying = false
        phase = BreathPhase.Inhale
        progress = 0f
        sessionStarted = false
    }

    fun updateProgress(deltaTime: Long) {
        if (!isPlaying) return

        val currentDuration = when (phase) {
            BreathPhase.Inhale -> inhaleDuration
            BreathPhase.Hold1 -> hold1Duration
            BreathPhase.Exhale -> exhaleDuration
            BreathPhase.Hold2 -> hold2Duration
        }

        progress += (deltaTime / 1000f) / currentDuration

        if (progress >= 1f) {
            progress = 0f
            phase = when (phase) {
                BreathPhase.Inhale -> BreathPhase.Hold1
                BreathPhase.Hold1 -> BreathPhase.Exhale
                BreathPhase.Exhale -> BreathPhase.Hold2
                BreathPhase.Hold2 -> BreathPhase.Inhale
            }
        }
    }

    fun getSpiralScale(): Float {
        return when (phase) {
            BreathPhase.Inhale -> 0.3f + (progress * 0.7f)
            BreathPhase.Exhale -> 1f - (progress * 0.7f)
            BreathPhase.Hold1 -> 1f
            BreathPhase.Hold2 -> 0.3f
        }
    }

    private fun markTodayComplete() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        completedDates = completedDates + today
    }

    fun isDateCompleted(date: LocalDate): Boolean {
        return completedDates.contains(date.toString())
    }
}

enum class BreathPhase(val label: String) {
    Inhale("Breathe In"),
    Hold1("Hold"),
    Exhale("Breathe Out"),
    Hold2("Hold")
}
