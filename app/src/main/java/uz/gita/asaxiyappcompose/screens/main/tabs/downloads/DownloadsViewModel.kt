package uz.gita.asaxiyappcompose.screens.main.tabs.downloads

import kotlinx.coroutines.flow.StateFlow
import uz.gita.asaxiyappcompose.data.model.UserBookData

interface DownloadsViewModel {
    val uiState: StateFlow<DownloadsState>
    fun onEventDispatchers(intent: DownloadsIntent)
    sealed interface DownloadsIntent {
        data object ShowBooks : DownloadsIntent
        data class Download(
            val book: UserBookData
        ) : DownloadsIntent

        data class OpenAudioBook(
            val book: UserBookData
        ) : DownloadsIntent

        data class OpenPdfBook(
            val book: UserBookData
        ) : DownloadsIntent
    }
}