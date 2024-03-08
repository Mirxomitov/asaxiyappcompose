package uz.gita.asaxiyappcompose.screens.main.tabs.downloads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: AppRepository,
) : ViewModel(), DownloadsViewModel {
    override val uiState = MutableStateFlow(DownloadsState(listOf()))

    override fun onEventDispatchers(intent: DownloadsViewModel.DownloadsIntent) {
        when(intent) {
            is DownloadsViewModel.DownloadsIntent.ShowBooks -> {
                repository.allUserBooks().onEach {
                    it.onSuccess {
                        logger("DownloadsViewModel.DownloadsIntent.ShowBooks.allUserBooks() =$it")
                        uiState.value.books = it
                    }
                    it.onFailure {
                        logger("DownloadsViewModel.DownloadsIntent.ShowBooks" +( it.message ?: "Unknown message"))
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}

