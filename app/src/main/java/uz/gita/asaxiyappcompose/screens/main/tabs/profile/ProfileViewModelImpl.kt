package uz.gita.asaxiyappcompose.screens.main.tabs.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class ProfileViewModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: AppRepository,
) : ViewModel(), ProfileViewModel {
    override val uiState = MutableStateFlow(ProfileState())

    override fun onEventDispatchers(intent: ProfileViewModel.ProfileIntent) {
        when (intent) {
            is ProfileViewModel.ProfileIntent.ShowUserData -> {
                repository.getUserDataByToken().onEach { userData ->
                    userData.onSuccess { userdata ->
//                        forwardUserData.emit(userdata)
                        uiState.update { state ->
                            state.copy(
                                firstName = userdata.firstName,
                                lastName = userdata.lastName,
                                email = userdata.gmail
                            )
                        }
                    }
                    userData.onFailure {
//                        error.emit(it.message ?: "Unknown error")
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}

