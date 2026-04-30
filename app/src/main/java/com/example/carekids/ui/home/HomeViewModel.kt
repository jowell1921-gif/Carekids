package com.example.carekids.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ── UiState ──────────────────────────────────────────────────────────────────
// Representa TODO lo que la pantalla Home necesita para dibujarse.
// La UI nunca calcula ni transforma datos — solo lee este objeto.

data class HomeUiState(
    val userName: String = "",
    val greetingMessage: String = "¡Bienvenido a CareKids!"
)

// ── ViewModel ─────────────────────────────────────────────────────────────────
// Expone el estado via StateFlow (inmutable hacia fuera, mutable solo aquí).
// La UI observa _uiState y recompone solo cuando cambia.

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onUserNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(
            userName = name,
            greetingMessage = if (name.isBlank()) "¡Bienvenido a CareKids!"
                              else "¡Hola, $name!"
        )
    }
}
