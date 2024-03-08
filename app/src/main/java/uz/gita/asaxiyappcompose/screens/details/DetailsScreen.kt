package uz.gita.asaxiyappcompose.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import uz.gita.asaxiyappcompose.R
import uz.gita.asaxiyappcompose.data.model.AudioBookData
import uz.gita.asaxiyappcompose.utils.logger

class DetailsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: DetailsViewModel = getViewModel<DetailsViewModelImpl>()
        DetailsContent(viewModel.uiState.collectAsState().value, viewModel::onEventDispatchers)
    }
}

@Composable
private fun DetailsContent(uiState: DetailsState, eventDispatcher: (DetailsViewModel.DetailsIntent) -> Unit) {
    eventDispatcher(DetailsViewModel.DetailsIntent.ShowBook(AudioBookData()))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Black),
            ) {
                Text(text = "Book", fontSize = 18.sp, color = Color.White, modifier = Modifier.align(Alignment.Center))
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier
                    .padding(24.dp)
                    .height(300.dp)
                    .width(200.dp)
                    .padding(12.dp)
            ) {
                AsyncImage(
                    model = uiState.bookData.img,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.audio_book),
                    error = painterResource(id = R.drawable.audio_book),
                    contentDescription = "",

                    )
            }

            Text(text = uiState.bookData.name, fontSize = 18.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold)
            Text(text = uiState.bookData.author, fontSize = 12.sp)

            var isPurchased by remember { mutableStateOf(!uiState.bookData.purchased) }
            logger("DetailsContent.purchased =${uiState.bookData.purchased}")
            Button(
                onClick = {
                    eventDispatcher(
                        DetailsViewModel.DetailsIntent.BuyBook(
                            uiState.bookData.name, ""/*Nav Args da olib o'tilgan*/
                        )
                    )
                    isPurchased = false
                },
                enabled = isPurchased,
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(text = "BUY BOOK")
            }

            Text(modifier = Modifier.padding(top = 4.dp), text = "Description:", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(modifier = Modifier.padding(top = 8.dp), text = uiState.bookData.description, fontSize = 14.sp)
        }
    }
}