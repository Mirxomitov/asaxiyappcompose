package uz.gita.asaxiyappcompose.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import uz.gita.asaxiyappcompose.R
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

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.home_tab)
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {

        }
    }
}

object BooksTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.books_tab)
            val icon = rememberVectorPainter(Icons.Default.ShoppingCart)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)) {

        }
    }
}

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.profile_tab)
            val icon = rememberVectorPainter(Icons.Default.AccountCircle)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {

        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}