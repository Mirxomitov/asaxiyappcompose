package uz.gita.asaxiyappcompose.screens.main.tabs.downloads

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: AppRepository,
) : ViewModel() {
    fun onEventDispatchers(intent: DownloadsIntent) {

    }
}

sealed interface DownloadsIntent {

}