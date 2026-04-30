package com.example.carekids.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ── Avatares y mascotas disponibles ──────────────────────────────────────────

data class AvatarOption(val emoji: String, val label: String)
data class PetOption(val emoji: String, val name: String)

val avatarOptions = listOf(
    AvatarOption("🦸", "Súper héroe"),
    AvatarOption("🧙", "Mago"),
    AvatarOption("🧚", "Hada"),
    AvatarOption("🦊", "Zorro"),
    AvatarOption("🐸", "Rana"),
    AvatarOption("🌟", "Estrella")
)

val petOptions = listOf(
    PetOption("🐶", "Perrito"),
    PetOption("🐱", "Gatito"),
    PetOption("🐹", "Hámster"),
    PetOption("🐰", "Conejito")
)

// ── UiState ───────────────────────────────────────────────────────────────────

data class ProfileUiState(
    val name: String = "",
    val age: Int = 8,
    val selectedAvatarIndex: Int = 0,
    val selectedPetIndex: Int = 0,
    val isSaved: Boolean = false
)

// ── ViewModel ─────────────────────────────────────────────────────────────────

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onAgeIncreased() {
        val current = _uiState.value.age
        if (current < 14) _uiState.value = _uiState.value.copy(age = current + 1)
    }

    fun onAgeDecreased() {
        val current = _uiState.value.age
        if (current > 4) _uiState.value = _uiState.value.copy(age = current - 1)
    }

    fun onAvatarSelected(index: Int) {
        _uiState.value = _uiState.value.copy(selectedAvatarIndex = index)
    }

    fun onPetSelected(index: Int) {
        _uiState.value = _uiState.value.copy(selectedPetIndex = index)
    }

    fun saveProfile() {
        // Fase 6: aquí se guardará en Firebase.
        // Por ahora marcamos como guardado para que la UI pueda navegar atrás.
        _uiState.value = _uiState.value.copy(isSaved = true)
    }
}
