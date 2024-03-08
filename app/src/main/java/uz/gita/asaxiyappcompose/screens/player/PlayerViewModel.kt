package uz.gita.asaxiyappcompose.screens.player

import kotlinx.coroutines.flow.StateFlow

interface PlayerViewModel {
    val uiState : StateFlow<PlayerState>
    fun eventDispatcher(intent: PlayerIntent)

    sealed interface PlayerIntent {
        data object GetMusicData : PlayerIntent
        data object Back : PlayerIntent
    }
}