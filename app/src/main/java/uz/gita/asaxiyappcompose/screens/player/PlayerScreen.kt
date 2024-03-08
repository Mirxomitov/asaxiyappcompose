package uz.gita.asaxiyappcompose.screens.player

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import uz.gita.asaxiyappcompose.R
import uz.gita.asaxiyappcompose.data.model.UserBookData
import uz.gita.asaxiyappcompose.ui.theme.AsaxiyAppComposeTheme
import java.io.File

class PlayerScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: PlayerViewModel = getViewModel<PlayerViewModelImpl>()
        PlayerContent(viewModel.uiState.collectAsState().value, viewModel::eventDispatcher)
    }
}

@Composable
private fun PlayerContent(uiState: PlayerState, eventDispatcher: (PlayerViewModel.PlayerIntent) -> Unit) {
    eventDispatcher(PlayerViewModel.PlayerIntent.GetMusicData)
    var bookData by remember { mutableStateOf(UserBookData()) }

    if (uiState.bookData.path != "") {
        val mediaPlayer = MediaPlayer.create(LocalContext.current, Uri.fromFile(File(uiState.bookData.path)))
        mediaPlayer.start()
        bookData = uiState.bookData
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.Black),
        ) {
            Text(text = "Music", fontSize = 18.sp, color = Color.White, modifier = Modifier.align(Alignment.Center))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            AsyncImage(
                model = uiState.bookData.img,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.audio_book),
                error = painterResource(id = R.drawable.audio_book),
                contentDescription = ""
            )

            Text(text = bookData.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = bookData.author, fontSize = 18.sp, fontWeight = FontWeight.Light)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            var sliderPosition by remember { mutableFloatStateOf(0f) }
            Column {
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Text(text = sliderPosition.toString())
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24), contentDescription = "",
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Black
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "",
                    modifier = Modifier
                        .size(96.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Black
                )

                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "",
                    Modifier
                        .rotate(180f)
                        .size(56.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewContent() {
    AsaxiyAppComposeTheme {
        PlayerContent(PlayerState(), {})
    }
}