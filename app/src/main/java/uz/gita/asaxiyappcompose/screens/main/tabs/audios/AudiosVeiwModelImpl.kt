package uz.gita.asaxiyappcompose.screens.main.tabs.audios

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
import uz.gita.asaxiyappcompose.screens.categories.CategoryScreen
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class AudiosViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: AppRepository,
) : ViewModel(), AudiosViewModel {

    override val uiState = MutableStateFlow(AudiosState())

    override fun onEventDispatchers(intent: AudiosViewModel.AudiosIntent) {
        logger("AudiosViewModelImpl.onEventDispatchers.intent=$intent")
        when (intent) {
            is AudiosViewModel.AudiosIntent.OpenAudio -> {
//                appNavigator.navigate(Screen(intent.book))
            }

            is AudiosViewModel.AudiosIntent.OpenCategory -> {
                viewModelScope.launch {
                    repository.currentCategory = intent.category
                    repository.currentType = intent.type
                    appNavigator.navigate(CategoryScreen())
                }
            }

            is AudiosViewModel.AudiosIntent.ShowAudios -> {
                repository.loadCategories().onEach { it ->
                    it.onSuccess {
                        logger("Success get data ${it.size}")
                        uiState.update { state ->
                            state.allCategoryAudios = it
                            logger("Success get data update -> $it")
                            state
                        }
                    }

                    it.onFailure {
                        logger("HELLO WORLD")
                        uiState.update { state ->
                            state.allCategoryAudios = emptyList()
                            state
                        }
                    }
                }.launchIn(viewModelScope)

            }
        }
    }
}
