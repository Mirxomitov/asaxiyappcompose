package uz.gita.asaxiyappcompose.screens.main.tabs.books

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.asaxiyappcompose.screens.main.tabs.audios.AudiosIntent
import uz.gita.asaxiyappcompose.screens.main.tabs.audios.AudiosViewModel
import uz.gita.asaxiyappcompose.utils.logger


class BooksScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<BooksViewModel>()
        BooksContent(viewModel::onEventDispatchers)
    }
}

@Composable
fun BooksContent(eventDispatchers: (BooksIntent) -> Unit) {
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