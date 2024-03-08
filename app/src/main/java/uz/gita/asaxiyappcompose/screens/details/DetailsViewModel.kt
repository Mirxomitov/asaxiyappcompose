package uz.gita.asaxiyappcompose.screens.details

import kotlinx.coroutines.flow.StateFlow
import uz.gita.asaxiyappcompose.data.model.AudioBookData

interface DetailsViewModel {
    val uiState: StateFlow<DetailsState>
    fun onEventDispatchers(intent: DetailsIntent)
    sealed interface DetailsIntent {
        data object Back : DetailsIntent
        data class ShowBook(var data : AudioBookData) : DetailsIntent
        data class BuyBook(var bookName : String, var bookType : String) : DetailsIntent
    }
}