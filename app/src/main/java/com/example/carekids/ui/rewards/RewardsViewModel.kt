package com.example.carekids.ui.rewards

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carekids.data.db.CareKidsDatabase
import com.example.carekids.data.db.EmotionalEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// ── Modelo de un badge ────────────────────────────────────────────────────────

data class Badge(
    val id: String,
    val emoji: String,
    val name: String,
    val description: String,
    val isUnlocked: (entries: List<EmotionalEntry>) -> Boolean
)

val allBadges = listOf(
    Badge("first_emotion",  "🌟", "Primera emoción",     "Registraste cómo te sentías por primera vez")
        { it.size >= 1 },
    Badge("explorer",       "🎭", "Explorador",           "Registraste 5 veces cómo te sentías")
        { it.size >= 5 },
    Badge("writer",         "📝", "Escritor",             "Escribiste una nota 3 veces")
        { it.count { e -> e.note.isNotBlank() } >= 3 },
    Badge("brave",          "💪", "Valiente",             "Contaste cuando tenías miedo o nervios")
        { it.any { e -> e.emotionName == "SCARED" || e.emotionName == "NERVOUS" } },
    Badge("happy_star",     "😄", "Alma alegre",          "Estuviste muy feliz 3 veces")
        { it.count { e -> e.emotionName == "HAPPY" } >= 3 },
    Badge("rainbow",        "🌈", "Arcoíris",             "Sentiste los 8 tipos de emociones")
        { entries -> entries.map { e -> e.emotionName }.toSet().size >= 8 },
    Badge("hero",           "🏆", "Superhéroe",           "Registraste 10 veces cómo te sentías")
        { it.size >= 10 },
    Badge("golden_heart",   "🤗", "Corazón sincero",      "Fuiste honesto cuando estabas triste")
        { it.any { e -> e.emotionName == "SAD" } }
)

// ── UiState ───────────────────────────────────────────────────────────────────

data class BadgeUiState(
    val badge: Badge,
    val unlocked: Boolean
)

data class RewardsUiState(
    val badges: List<BadgeUiState> = emptyList(),
    val carePoints: Int            = 0,
    val totalEntries: Int          = 0
)

// ── ViewModel ─────────────────────────────────────────────────────────────────

class RewardsViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CareKidsDatabase.getInstance(application).emotionalDao()

    // Toda la lógica de desbloqueo vive aquí: mapeamos las entradas a badges.
    // La UI solo recibe el UiState calculado — no sabe nada de Room ni de badges.
    val uiState: StateFlow<RewardsUiState> = dao.getAllEntries()
        .map { entries ->
            val badgeStates = allBadges.map { badge ->
                BadgeUiState(badge = badge, unlocked = badge.isUnlocked(entries))
            }
            // CarePoints: 10 por entrada + 5 de bonus si tiene nota
            val points = entries.size * 10 + entries.count { it.note.isNotBlank() } * 5
            RewardsUiState(
                badges       = badgeStates,
                carePoints   = points,
                totalEntries = entries.size
            )
        }
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = RewardsUiState()
        )
}
