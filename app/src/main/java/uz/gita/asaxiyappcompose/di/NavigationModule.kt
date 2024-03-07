package uz.gita.asaxiyappcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.asaxiyappcompose.navigation.AppNavigationHandler
import uz.gita.asaxiyappcompose.navigation.AppNavigator
import uz.gita.asaxiyappcompose.navigation.AppNavigatorDispatcher

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindNavigation(dispatcher: AppNavigatorDispatcher): AppNavigator

    @Binds
    fun bindHandler(dispatcher: AppNavigatorDispatcher): AppNavigationHandler
}