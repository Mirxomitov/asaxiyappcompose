package uz.gita.asaxiyappcompose.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.screens.details.DetailsScreen
import uz.gita.asaxiyappcompose.screens.details.DetailsState
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class CategoryViewModelImpl @Inject constructor(
    private val repository: AppRepository,
    private val appNavigator: AppNavigator
) : ViewModel(), CategoryViewModel {
    override fun onEventDispatchers(intent: CategoryViewModel.CategoryIntent) {
        when (intent) {
            is CategoryViewModel.CategoryIntent.OpenBook -> {
                viewModelScope.launch {
                    repository.currentType = intent.bookType
                    repository.currentBookName = intent.bookName
                    appNavigator.navigate(DetailsScreen())
                }
            }

            CategoryViewModel.CategoryIntent.Back -> {
                viewModelScope.launch {
                    appNavigator.back()
                }
            }

            is CategoryViewModel.CategoryIntent.ShowBooksByCategory -> {
                logger("CATEGORY DATA IS LOADING")
                intent.category = repository.currentCategory
                intent.type = repository.currentType

                repository.loadAudios(
                    intent.category, intent.type
                ).onEach {
                    it.onSuccess { ls ->
                        uiState.value.bookList = ls
                        logger("CATEGORY DATA SIZE =${ls.size}")
                    }
                    it.onFailure { exception ->
                        logger(exception.message ?: "Unknown message load by category")
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    override val uiState = MutableStateFlow(CategoryState())
}