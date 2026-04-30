package com.example.carekids.ui.emotional

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carekids.data.db.CareKidsDatabase
import com.example.carekids.data.db.EmotionalEntry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Emotion(val emoji: String, val label: String, val message: String) {
    HAPPY    ("😄", "¡Feliz!",       "¡Me alegra que estés bien hoy! 🌟"),
    CALM     ("😊", "Tranquilo",     "Qué bien estás hoy 😊 Sigue así."),
    NERVOUS  ("😰", "Nervioso",      "Es normal sentir nervios. ¡Tú puedes! 💪"),
    SAD      ("😢", "Triste",        "Está bien estar triste. Aquí estoy contigo 🤗"),
    SCARED   ("😨", "Con miedo",     "El miedo pasa. Respira hondo conmigo 🌈"),
    TIRED    ("😴", "Cansado",       "Descansar también es importante. Cuídate 💙"),
    ANGRY    ("😤", "Enojado",       "Esos sentimientos son válidos. Cuéntame qué pasó."),
    CONFUSED ("🤔", "Confundido",    "¿Muchas cosas en la cabeza? Todo se va a aclarar ✨")
}

data class EmotionalUiState(
    val selectedEmotion: Emotion?  = null,
    val note: String               = "",
    val isSaved: Boolean           = false,
    val showCelebration: Boolean   = false
)

// AndroidViewModel recibe Application en el constructor.
// Úsalo siempre que el ViewModel necesite el contexto de Android (base de datos, recursos, etc.).
// NUNCA pases un Context de Activity/Fragment al ViewModel — se destruiría y causaría memory leaks.

class EmotionalViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CareKidsDatabase.getInstance(application).emotionalDao()

    private val _uiState = MutableStateFlow(EmotionalUiState())
    val uiState: StateFlow<EmotionalUiState> = _uiState.asStateFlow()

    fun onEmotionSelected(emotion: Emotion) {
        _uiState.value = _uiState.value.copy(selectedEmotion = emotion, isSaved = false)
    }

    fun onNoteChanged(text: String) {
        if (text.length <= 200) {
            _uiState.value = _uiState.value.copy(note = text)
        }
    }

    fun onSave() {
        val emotion = _uiState.value.selectedEmotion ?: return
        viewModelScope.launch {
            dao.insert(
                EmotionalEntry(
                    emotionName = emotion.name,         // "HAPPY", "SAD", etc.
                    note        = _uiState.value.note,
                    timestamp   = System.currentTimeMillis()
                )
            )
            _uiState.value = _uiState.value.copy(showCelebration = true)
            delay(1800)
            _uiState.value = _uiState.value.copy(showCelebration = false, isSaved = true)
        }
    }
}
