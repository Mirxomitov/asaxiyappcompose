package uz.gita.asaxiyappcompose.screens.main.tabs.books

import kotlinx.coroutines.flow.StateFlow
import uz.gita.asaxiyappcompose.data.model.DataUI

interface BooksViewModel {
    fun onEventDispatchers(intent: BooksIntent)
    val uiState: StateFlow<BooksState>

    sealed interface BooksIntent {
        data class OpenBook(val book: DataUI) : BooksIntent
        data class OpenCategory(
            val category: String
        ) : BooksIntent

        data object ShowBooks : BooksIntent
    }
}

