package uz.gita.asaxiyappcompose.screens.main.tabs.downloads

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import uz.gita.asaxiyappcompose.R

class DownloadsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<DownloadsViewModelImpl>()
        DownloadsContent(viewModel.uiState.collectAsState().value, viewModel::onEventDispatchers)
    }
}

@Composable
fun DownloadsContent(uiState: DownloadsState, eventDispatchers: (DownloadsViewModel.DownloadsIntent) -> Unit) {
    eventDispatchers(DownloadsViewModel.DownloadsIntent.ShowBooks)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.Black),
        ) {
            Text(text = "My Books", fontSize = 18.sp, color = Color.White, modifier = Modifier.align(Alignment.Center))
        }
        LazyColumn {
            items(uiState.books) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(144.dp)
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            eventDispatchers(
                                if (it.type == "audios") DownloadsViewModel.DownloadsIntent.OpenAudioBook(it)
                                else DownloadsViewModel.DownloadsIntent.OpenPdfBook(it)
                            )
                        },
                    colors = CardDefaults.cardColors(containerColor = Color.White),

                    ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = it.img,
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .width(96.dp)
                                .height(132.dp),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.audio_book),
                            error = painterResource(id = R.drawable.audio_book),
                            contentDescription = ""
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .padding(start = 16.dp)
                        ) {
                            Text(text = it.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = it.author, fontWeight = FontWeight.Light, fontSize = 12.sp)
                        }

                        if (!it.download) {
                            Icon(
                                painter = painterResource(id = R.drawable.download), contentDescription = "download icon",
                                modifier = Modifier
                                    .size(56.dp)
                                    .align(Alignment.Bottom)
                                    .padding(12.dp)
                                    .clickable {
                                        eventDispatchers(DownloadsViewModel.DownloadsIntent.Download(it))
                                        eventDispatchers(DownloadsViewModel.DownloadsIntent.ShowBooks)
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}