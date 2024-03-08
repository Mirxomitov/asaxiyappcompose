package uz.gita.asaxiyappcompose.screens.main.tabs.downloads

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel


class DownloadsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<DownloadsViewModel>()
        DownloadsContent(viewModel::onEventDispatchers)
    }
}

@Composable
fun DownloadsContent(eventDispatchers: (DownloadsIntent) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        items(10) {
            Text(text = "TEXT VIEW $it")
        }
    }
}