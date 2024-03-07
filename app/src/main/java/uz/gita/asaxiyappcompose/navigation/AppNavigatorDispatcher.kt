package uz.gita.asaxiyappcompose.navigation

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNavigatorDispatcher @Inject constructor() : AppNavigator, AppNavigationHandler {
    override val backStack = MutableSharedFlow<AppNavigationArgs>()

    private suspend fun navigator(arg: AppNavigationArgs) {
        backStack.emit(arg)
    }

    override suspend fun back() = navigator{
        val result = pop()
        Log.d("TTT", "uz.gita.asaxiyappcompose.navigation.AppNavigatorDispatcher.back().result=$result")
    }

    override suspend fun navigate(screen: AndroidScreen) = navigator {
        push(screen)
    }

    override suspend fun replace(screen: AndroidScreen) = navigator {
        replace(screen)
    }
}