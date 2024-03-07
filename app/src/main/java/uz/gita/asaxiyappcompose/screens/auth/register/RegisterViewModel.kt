package uz.gita.asaxiyappcompose.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.asaxiyappcompose.data.model.UserData
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.screens.auth.login.LogInScreen
import uz.gita.asaxiyappcompose.screens.main.MainScreen
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AppRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {
    fun onEventDispatcher(intent: RegisterIntent) {
        when (intent) {
            RegisterIntent.SignIn -> {
                viewModelScope.launch {
                    appNavigator.replace(LogInScreen())
                }
            }

            is RegisterIntent.CreateAccount -> {
                repository.register(
                    userData = intent.userData,
                    password = intent.password
                ).onEach {
                    it.onSuccess {
                        appNavigator.replace(MainScreen())
                    }
                    it.onFailure { logger("REGISTER FAILED") }
                }.launchIn(viewModelScope)
            }
        }
    }
}

sealed interface RegisterIntent {
    data object SignIn : RegisterIntent
    data class CreateAccount(
        val userData: UserData,
        val password: String
    ) : RegisterIntent
}