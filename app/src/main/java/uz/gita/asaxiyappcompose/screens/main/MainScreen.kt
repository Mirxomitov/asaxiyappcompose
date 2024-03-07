package uz.gita.asaxiyappcompose.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import uz.gita.asaxiyappcompose.ui.theme.AsaxiyAppComposeTheme

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<MainViewModel>()
        MainContent(eventDispatcher = viewModel::onEventDispatcher)
    }
}

@Composable
private fun MainContent(eventDispatcher: (MainIntent) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabNavigator(HomeTab) {
            Scaffold(
                content = { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(BooksTab)
                        TabNavigationItem(AudiosTab)
                        TabNavigationItem(ProfileTab)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewContent() {
    AsaxiyAppComposeTheme {
        MainContent({})
    }
}
