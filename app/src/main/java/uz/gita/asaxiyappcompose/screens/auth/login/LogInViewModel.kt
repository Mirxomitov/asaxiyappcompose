package uz.gita.asaxiyappcompose.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.asaxiyappcompose.domain.AppRepository
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.screens.auth.register.RegisterScreen
import uz.gita.asaxiyappcompose.screens.main.MainScreen
import uz.gita.asaxiyappcompose.utils.logger
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val repository: AppRepository,
) : ViewModel() {
    fun onEventDispatcher(intent: LogInIntent) {
        when (intent) {
            LogInIntent.CreateAccount -> {
                viewModelScope.launch { appNavigator.replace(RegisterScreen()) }
            }

            is LogInIntent.LogIn -> {
                repository.login(intent.email, intent.password).onEach {
                    it.onSuccess {
                        appNavigator.replace(MainScreen())
                    }
                    it.onFailure {

                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}

sealed interface LogInIntent {
    //data object LogIn : LogInIntent
    data object CreateAccount : LogInIntent
    data class LogIn(val email: String, val password: String) : LogInIntent
}