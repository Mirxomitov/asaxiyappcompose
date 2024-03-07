package uz.gita.asaxiyappcompose.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import uz.gita.asaxiyappcompose.screens.auth.login.LogInScreen
import uz.gita.asaxiyappcompose.ui.theme.AsaxiyAppComposeTheme

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigation = LocalNavigator.currentOrThrow
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            navigation.replace(LogInScreen())
        }
        ScreenContent()
    }
}


@Composable
private fun ScreenContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "Welcome", fontSize = 32.sp, modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewContent() {
    AsaxiyAppComposeTheme {
        ScreenContent()
    }
}