package uz.gita.asaxiyappcompose.screens.main.tabs.books

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
import uz.gita.asaxiyappcompose.screens.details.DetailsScreen
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class BooksViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: AppRepository,
) : ViewModel(), BooksViewModel {

    override val uiState = MutableStateFlow(BooksState())

    override fun onEventDispatchers(intent: BooksViewModel.BooksIntent) {

        when (intent) {
            is BooksViewModel.BooksIntent.OpenBook -> {
                viewModelScope.launch {
                    repository.currentBookName = intent.book.name
                    repository.currentType = "books"
                    appNavigator.navigate(DetailsScreen())
                }
            }

            is BooksViewModel.BooksIntent.OpenCategory -> {
                viewModelScope.launch {
                    repository.currentCategory = intent.category
                    repository.currentType = intent.type
                    appNavigator.navigate(CategoryScreen())
                }
            }

            is BooksViewModel.BooksIntent.ShowBooks -> {
                repository.getBooksByCategoryMap().onEach { it ->
                    it.onSuccess {
                        logger("Success get data ${it.size}")
//                        uiState.update { state ->
//                            state.allCategoryBooksList = it
//                            logger("Success get data update -> $it")
//                            state
//                        }
                        uiState.emit(BooksState(it))
                    }

                    it.onFailure {
                        logger("HELLO WORLD")
//                        uiState.update { state ->
//                            state.allCategoryBooksList = emptyList()
//                            state
//                        }
                        uiState.emit(BooksState(emptyList()))
                    }
                }.launchIn(viewModelScope)

            }
        }
    }
}


