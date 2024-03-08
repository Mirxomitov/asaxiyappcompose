package uz.gita.asaxiyappcompose.screens.main.tabs.audios

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import uz.gita.asaxiyappcompose.data.model.DataUI
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.screens.main.tabs.books.BooksState
import uz.gita.asaxiyappcompose.screens.main.tabs.books.BooksViewModel
import javax.inject.Inject

interface AudiosViewModel {
        fun onEventDispatchers(intent: AudiosIntent)
        val uiState: StateFlow<AudiosState>
    sealed interface AudiosIntent {
        data class OpenAudio(val book: DataUI) : AudiosIntent
        data class OpenCategory(
            val category: String,
            val type : String
        ) : AudiosIntent

        data object ShowAudios : AudiosIntent
    }
}