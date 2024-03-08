package uz.gita.asaxiyappcompose.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class PlayerViewModelImpl @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator
) : ViewModel(), PlayerViewModel {
    override val uiState = MutableStateFlow(PlayerState())

    override fun eventDispatcher(intent: PlayerViewModel.PlayerIntent) {
        when (intent) {
            is PlayerViewModel.PlayerIntent.GetMusicData -> {
                uiState.update { state ->
                    state.copy(bookData = repository.currentBook)
                }
            }

            PlayerViewModel.PlayerIntent.Back -> {
                viewModelScope.launch {
                    navigator.back()
                }
            }
        }
    }
}