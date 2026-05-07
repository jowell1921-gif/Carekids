package com.example.carekids.ui.stories

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.carekids.data.model.Story
import com.example.carekids.data.model.allStories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class StoryReaderUiState(
    val story: Story? = null,
    val currentPage: Int = 0,
    val pointsAwarded: Boolean = false,
    val showCelebration: Boolean = false
)

class StoryReaderViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val storyId: String = checkNotNull(savedStateHandle["storyId"])

    private val _uiState = MutableStateFlow(StoryReaderUiState())
    val uiState: StateFlow<StoryReaderUiState> = _uiState.asStateFlow()

    init {
        val story = allStories.find { it.id == storyId }
        _uiState.update { it.copy(story = story) }
    }

    fun nextPage() {
        val state = _uiState.value
        val story = state.story ?: return
        val next = state.currentPage + 1
        if (next >= story.pages.size) return

        val isLastPage = next == story.pages.size - 1
        _uiState.update {
            it.copy(
                currentPage = next,
                showCelebration = isLastPage && !it.pointsAwarded,
                pointsAwarded = if (isLastPage) true else it.pointsAwarded
            )
        }
    }

    fun previousPage() {
        val state = _uiState.value
        if (state.currentPage == 0) return
        _uiState.update { it.copy(currentPage = it.currentPage - 1) }
    }

    fun dismissCelebration() {
        _uiState.update { it.copy(showCelebration = false) }
    }
}
