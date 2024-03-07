package uz.gita.asaxiyappcompose.navigation

import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.flow.Flow

interface AppNavigationHandler {
    val backStack: Flow<AppNavigationArgs>
}

typealias AppNavigationArgs = Navigator.() -> Unit