package com.example.carekids.ui.learn

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carekids.data.model.Disease
import com.example.carekids.data.model.allDiseases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AnswerState { WAITING, CORRECT, WRONG }

data class MythGameUiState(
    val disease: Disease?       = null,
    val currentIndex: Int       = 0,
    val totalStatements: Int    = 0,
    val answerState: AnswerState = AnswerState.WAITING,
    val explanation: String     = "",
    val score: Int              = 0,
    val isFinished: Boolean     = false,
    val carePointsEarned: Int   = 0
)

// SavedStateHandle permite leer los argumentos de navegación directamente en el ViewModel.
// Navigation Compose los inyecta automáticamente — no necesitamos pasarlos manualmente.

class MythGameViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val diseaseId: String = checkNotNull(savedStateHandle["diseaseId"])
    private val disease: Disease  = allDiseases.first { it.id == diseaseId }

    private val _uiState = MutableStateFlow(
        MythGameUiState(
            disease        = disease,
            totalStatements = disease.statements.size
        )
    )
    val uiState: StateFlow<MythGameUiState> = _uiState.asStateFlow()

    fun onAnswer(answeredReality: Boolean) {
        val state    = _uiState.value
        if (state.answerState != AnswerState.WAITING) return

        val statement   = disease.statements[state.currentIndex]
        val isCorrect   = answeredReality == statement.isReality
        val newScore    = if (isCorrect) state.score + 1 else state.score

        _uiState.value = state.copy(
            answerState  = if (isCorrect) AnswerState.CORRECT else AnswerState.WRONG,
            explanation  = statement.explanation,
            score        = newScore
        )
    }

    fun onNext() {
        val state = _uiState.value
        val nextIndex = state.currentIndex + 1

        if (nextIndex >= disease.statements.size) {
            // Juego terminado — calculamos CarePoints: 20 por acierto
            _uiState.value = state.copy(
                answerState      = AnswerState.WAITING,
                isFinished       = true,
                carePointsEarned = state.score * 20
            )
        } else {
            _uiState.value = state.copy(
                currentIndex = nextIndex,
                answerState  = AnswerState.WAITING,
                explanation  = ""
            )
        }
    }
}
