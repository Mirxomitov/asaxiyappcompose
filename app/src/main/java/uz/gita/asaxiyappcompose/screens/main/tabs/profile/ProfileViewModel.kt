package uz.gita.asaxiyappcompose.screens.main.tabs.profile

import kotlinx.coroutines.flow.StateFlow

interface ProfileViewModel {
    val uiState: StateFlow<ProfileState>
    fun onEventDispatchers (intent : ProfileIntent)

    sealed interface ProfileIntent {
        data object ShowUserData : ProfileIntent
    }
}