package uz.gita.asaxiyappcompose.screens.categories

import kotlinx.coroutines.flow.StateFlow

interface CategoryViewModel {
    fun onEventDispatchers(intent: CategoryIntent)
    val uiState: StateFlow<CategoryState>

    sealed interface CategoryIntent {
        data class OpenBook(
            val bookName: String, val bookType: String
        ) : CategoryIntent

        data object Back : CategoryIntent

        data class ShowBooksByCategory(
            var category: String,
            var type : String,
        ) : CategoryIntent
    }
}