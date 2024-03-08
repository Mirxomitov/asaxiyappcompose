package uz.gita.asaxiyappcompose.screens.main.tabs.audios

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import uz.gita.asaxiyappcompose.R
import uz.gita.asaxiyappcompose.screens.main.tabs.audios.AudiosViewModel.AudiosIntent.OpenCategory


class AudiosScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: AudiosViewModel = getViewModel<AudiosViewModelImpl>()
        AudiosContent(viewModel.uiState.collectAsState().value, viewModel::onEventDispatchers)
    }
}

@Composable
fun AudiosContent(uiState: AudiosState, eventDispatchers: (AudiosViewModel.AudiosIntent) -> Unit) {
    eventDispatchers(AudiosViewModel.AudiosIntent.ShowAudios)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(uiState.allCategoryAudios) { categoryData ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = categoryData.category)
                Text(text = "All books", modifier = Modifier.clickable {
                    eventDispatchers(
                        OpenCategory(
                            category = categoryData.category, type = "audios"
                        )
                    )
                })
            }
            LazyRow {
                items(categoryData.bookArr) {
                    Column(
                        modifier = Modifier
                            .height(144.dp)
                            .width(96.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = it.img,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(end = 10.dp),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.audio_book),
                            error = painterResource(id = R.drawable.audio_book),
                            contentDescription = ""
                        )

                        Text(text = it.name, fontSize = 18.sp)
                        Text(text = it.author, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}