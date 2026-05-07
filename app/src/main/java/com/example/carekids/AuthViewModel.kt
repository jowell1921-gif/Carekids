package com.example.carekids

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carekids.data.model.UserData
import com.example.carekids.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var user by mutableStateOf<UserData?>(null)
        private set

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.registerUser(name, email, password).fold(
                onSuccess = { userData -> user = userData },
                onFailure = { e -> errorMessage = e.message }
            )
            isLoading = false
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.loginUser(email, password).fold(
                onSuccess = { userData -> user = userData },
                onFailure = { e -> errorMessage = e.message }
            )
            isLoading = false
        }
    }

    fun clearError() { errorMessage = null }

    fun clearSuccessMessage() { user = null }

    fun logout() {
        repository.logout()
        user = null
    }
}
