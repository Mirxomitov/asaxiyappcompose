package uz.gita.asaxiyappcompose.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    private val repository: AppRepository,
    private val navigator: AppNavigator
) : ViewModel(), DetailsViewModel {

    override val uiState = MutableStateFlow(DetailsState())
    override fun onEventDispatchers(intent: DetailsViewModel.DetailsIntent) {
        when (intent) {
            is DetailsViewModel.DetailsIntent.ShowBook -> {
                repository.getAudioBookDetails(repository.currentBookName, repository.currentType).onEach {
                    it.onSuccess {
                        intent.data = it
                    }
                    it.onFailure {
                        logger(it.message ?: "Unknown Message")
                    }
                }.launchIn(viewModelScope)
            }

            DetailsViewModel.DetailsIntent.Back -> {
                viewModelScope.launch {
                    navigator.back()
                }
            }
        }
    }
}