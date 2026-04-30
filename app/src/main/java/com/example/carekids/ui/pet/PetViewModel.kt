package com.example.carekids.ui.pet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ── Nivel ─────────────────────────────────────────────────────────────────────
// El nivel se calcula automáticamente a partir de los puntos.
// Cada nivel tiene un nombre y un umbral mínimo de puntos.

enum class PetLevel(val label: String, val minPoints: Int, val emoji: String) {
    BABY    ("Bebé",      0,   "🥚"),
    SMALL   ("Pequeño",   100, "🌱"),
    MEDIUM  ("Creciendo", 250, "⭐"),
    BIG     ("Fuerte",    500, "💪"),
    CHAMPION("¡Campeón!", 800, "🏆")
}

enum class PetMood(val label: String) {
    HAPPY  ("¡Muy feliz!"),
    NEUTRAL("Bien"),
    TIRED  ("Necesita atención")
}

// ── UiState ───────────────────────────────────────────────────────────────────

data class PetUiState(
    val petEmoji: String       = "🐶",
    val petName: String        = "Mi mascota",
    val points: Int            = 0,
    val level: PetLevel        = PetLevel.BABY,
    val mood: PetMood          = PetMood.NEUTRAL,
    val xpProgress: Float      = 0f,      // 0.0 → 1.0 dentro del nivel actual
    val lastPointsEarned: Int  = 0,       // para mostrar "+N pts" en pantalla
    val showPointsAnim: Boolean = false
)

// ── ViewModel ─────────────────────────────────────────────────────────────────

class PetViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PetUiState())
    val uiState: StateFlow<PetUiState> = _uiState.asStateFlow()

    // Llamado desde NavGraph cuando se entra a la pantalla
    fun initPet(emoji: String, name: String) {
        _uiState.value = _uiState.value.copy(petEmoji = emoji, petName = name)
    }

    fun onFeed()  = addPoints(10)
    fun onPlay()  = addPoints(15)
    fun onHug()   = addPoints(5)

    private fun addPoints(earned: Int) {
        val newPoints = _uiState.value.points + earned
        val newLevel  = calculateLevel(newPoints)
        val newMood   = calculateMood(newPoints)
        val newXp     = calculateXp(newPoints, newLevel)

        _uiState.value = _uiState.value.copy(
            points           = newPoints,
            level            = newLevel,
            mood             = newMood,
            xpProgress       = newXp,
            lastPointsEarned = earned,
            showPointsAnim   = true
        )

        // Oculta la animación "+N pts" después de 1.5 segundos
        viewModelScope.launch {
            delay(1500)
            _uiState.value = _uiState.value.copy(showPointsAnim = false)
        }
    }

    // Devuelve el nivel correspondiente a los puntos actuales
    private fun calculateLevel(points: Int): PetLevel =
        PetLevel.entries.lastOrNull { points >= it.minPoints } ?: PetLevel.BABY

    // Mood: happy si subió de nivel recientemente, tired si lleva poco tiempo sin puntos
    private fun calculateMood(points: Int): PetMood = when {
        points >= PetLevel.CHAMPION.minPoints -> PetMood.HAPPY
        points % 50 < 15                      -> PetMood.HAPPY
        points % 50 < 35                      -> PetMood.NEUTRAL
        else                                  -> PetMood.TIRED
    }

    // Progreso dentro del nivel actual (0.0 a 1.0)
    private fun calculateXp(points: Int, level: PetLevel): Float {
        val levels   = PetLevel.entries
        val nextLevel = levels.getOrNull(level.ordinal + 1) ?: return 1f
        val range    = (nextLevel.minPoints - level.minPoints).toFloat()
        val progress = (points - level.minPoints).toFloat()
        return (progress / range).coerceIn(0f, 1f)
    }
}
