package uz.gita.asaxiyappcompose.screens.main.tabs.audios

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Black),
            ) {
                Text(text = "Audios", fontSize = 18.sp, color = Color.White, modifier = Modifier.align(Alignment.Center))
            }
        }
        items(uiState.allCategoryAudios) { categoryData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, end = 12.dp, start = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = categoryData.category, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "All books",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
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
                            .padding(end = 12.dp)
                            .width(96.dp)
                            .height(248.dp)
                            .clickable { eventDispatchers(AudiosViewModel.AudiosIntent.OpenAudio(type = "audios", bookName = it.name)) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AsyncImage(
                            model = it.img,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.audio_book),
                            error = painterResource(id = R.drawable.audio_book),
                            contentDescription = ""
                        )

                        Text(
                            text = it.name,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = it.author,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }
        }
    }
}