package com.example.carekids.ui.emotional

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carekids.data.db.CareKidsDatabase
import com.example.carekids.data.db.EmotionalEntry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class EmotionalHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CareKidsDatabase.getInstance(application).emotionalDao()

    // stateIn convierte el Flow de Room en un StateFlow que la UI puede observar.
    // WhileSubscribed(5000): mantiene la suscripción activa 5 segundos después de que la UI
    // deja de observar — evita recrear la query si el usuario rota la pantalla.
    val entries: StateFlow<List<EmotionalEntry>> = dao.getAllEntries()
        .stateIn(
            scope         = viewModelScope,
            started       = SharingStarted.WhileSubscribed(5_000),
            initialValue  = emptyList()
        )
}
