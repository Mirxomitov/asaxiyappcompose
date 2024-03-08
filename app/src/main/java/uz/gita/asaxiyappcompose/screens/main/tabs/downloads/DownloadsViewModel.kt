package uz.gita.asaxiyappcompose.screens.main.tabs.downloads

import kotlinx.coroutines.flow.StateFlow

interface DownloadsViewModel {
    val uiState : StateFlow<DownloadsState>
    fun onEventDispatchers(intent: DownloadsIntent)
    sealed interface DownloadsIntent {
        data object ShowBooks : DownloadsIntent
    }
}