package uz.gita.asaxiyappcompose.screens.main.tabs.audios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel

class AudiosScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<AudiosViewModel>()
        AudiosContent(viewModel::onEventDispatchers)
    }
}

@Composable
fun AudiosContent(eventDispatchers: (AudiosIntent) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        items(10) {
            Text(text = "TEXT VIEW $it")
        }
    }
}