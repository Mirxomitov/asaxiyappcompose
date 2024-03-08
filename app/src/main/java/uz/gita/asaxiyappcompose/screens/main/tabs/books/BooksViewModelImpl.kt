package uz.gita.asaxiyappcompose.screens.main.tabs.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class BooksViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: AppRepository,
) : ViewModel(), BooksViewModel {

    override val uiState = MutableStateFlow(BooksState())

    override fun onEventDispatchers(intent: BooksViewModel.BooksIntent) {
        logger("BooksViewModelImpl.onEventDispatchers.intent=$intent")
        when (intent) {
            is BooksViewModel.BooksIntent.OpenBook -> {
//                appNavigator.navigate(Screen(intent.book))
            }

            is BooksViewModel.BooksIntent.OpenCategory -> {
                //appNavigator.navigate(Screen(intent.category))
            }

            is BooksViewModel.BooksIntent.ShowBooks -> {
                repository.getBooksByCategoryMap().onEach { it ->
                    it.onSuccess {
                        logger("Success get data ${it.size}")
                        uiState.update { state ->
                            state.allCategoryBooksList = it
                            logger("Success get data update -> $it")
                            state
                        }
                    }

                    it.onFailure {
                        logger("HELLO WORLD")
                        uiState.update { state ->
                            state.allCategoryBooksList = emptyList()
                            state
                        }
                    }
                }.launchIn(viewModelScope)

            }
        }
    }
}


