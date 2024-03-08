package uz.gita.asaxiyappcompose.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    private val repository: AppRepository, private val navigator: AppNavigator
) : ViewModel(), DetailsViewModel {

    override val uiState = MutableStateFlow(DetailsState())
    override fun onEventDispatchers(intent: DetailsViewModel.DetailsIntent) {
        when (intent) {
            is DetailsViewModel.DetailsIntent.ShowBook -> {
                logger("SHOW BOOK getAudioBookDetails ${repository.currentBookName} ${repository.currentType}")
                repository.getAudioBookDetails(repository.currentBookName, repository.currentType).onEach {
                    it.onSuccess {
                        uiState.update { state -> state.copy(bookData = it) }
                        logger("DetailsViewModel.DetailsIntent.ShowBook.onSuccess -> ${it.name}")
                    }
                    it.onFailure {
                        logger("DetailsViewModel.DetailsIntent.ShowBook.onFailure -> $it")
                    }
                }.launchIn(viewModelScope)
            }

            DetailsViewModel.DetailsIntent.Back -> {
                viewModelScope.launch {
                    navigator.back()
                }
            }

            is DetailsViewModel.DetailsIntent.BuyBook -> {
                logger("DetailsViewModel.DetailsIntent.BuyBook -> repo.currenttype =" + repository.currentType)
                repository.buyBook(repository.currentBookName, repository.currentType).onEach {
                    it.onSuccess {
                        logger("SUCCESSFULLY BOUGHT")
                    }
                    it.onFailure {
                        logger("BOOK BOUGHT FAILURE")
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}