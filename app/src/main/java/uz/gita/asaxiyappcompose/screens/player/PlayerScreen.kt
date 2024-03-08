package uz.gita.asaxiyappcompose.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.CenterHorizontally)
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Music", fontSize = 18.sp, color = Color.White)
        }

        AsyncImage(
            model = uiState.bookData.img,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.audio_book),
            error = painterResource(id = R.drawable.audio_book),
            contentDescription = ""
        )

        Text(text = uiState.bookData.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = uiState.bookData.author, fontSize = 18.sp, fontWeight = FontWeight.Light)


    }
}