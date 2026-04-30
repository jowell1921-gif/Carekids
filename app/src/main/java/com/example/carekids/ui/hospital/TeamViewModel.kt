package com.example.carekids.ui.hospital

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carekids.data.db.CareKidsDatabase
import com.example.carekids.data.db.TeamMember
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Roles disponibles: emoji asignado automáticamente según el rol elegido
val teamRoles = listOf(
    "Doctor"          to "👨‍⚕️",
    "Doctora"         to "👩‍⚕️",
    "Enfermero"       to "🧑‍⚕️",
    "Enfermera"       to "👩‍⚕️",
    "Nutricionista"   to "🥗",
    "Fisioterapeuta"  to "💪",
    "Psicólogo"       to "🧠",
    "Otro"            to "⭐"
)

data class TeamUiState(
    val members: List<TeamMember>  = emptyList(),
    val showAddDialog: Boolean     = false,
    val nameInput: String          = "",
    val selectedRoleIndex: Int     = 0
)

class TeamViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CareKidsDatabase.getInstance(application).teamMemberDao()

    val members: StateFlow<List<TeamMember>> = dao.getAllMembers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _dialogState = kotlinx.coroutines.flow.MutableStateFlow(
        Triple(false, "", 0)  // showDialog, nameInput, roleIndex
    )
    val dialogState: StateFlow<Triple<Boolean, String, Int>> = _dialogState

    fun openDialog()  { _dialogState.value = Triple(true,  "", 0) }
    fun closeDialog() { _dialogState.value = Triple(false, "", 0) }

    fun onNameChanged(name: String)    { _dialogState.value = _dialogState.value.copy(second = name) }
    fun onRoleSelected(index: Int)     { _dialogState.value = _dialogState.value.copy(third = index) }

    fun saveMember() {
        val (_, name, roleIndex) = _dialogState.value
        if (name.isBlank()) return
        val (role, emoji) = teamRoles[roleIndex]
        viewModelScope.launch {
            dao.insert(TeamMember(name = name.trim(), role = role, emoji = emoji))
            closeDialog()
        }
    }

    fun deleteMember(member: TeamMember) {
        viewModelScope.launch { dao.delete(member) }
    }
}
