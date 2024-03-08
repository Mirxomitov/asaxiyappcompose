package uz.gita.asaxiyappcompose.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color.Black),
            ) {
                Text(text = "Book", fontSize = 18.sp, color = Color.White)
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier
                    .height(200.dp)
                    .width(100.dp)
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

            Button(
                onClick = {
                    eventDispatcher(
                        DetailsViewModel.DetailsIntent.BuyBook(
                            uiState.bookData.name, ""/*Nav Args da olib o'tilgan*/
                        )
                    )
                },
                enabled = !uiState.bookData.purchased
            ) {
                if (!uiState.bookData.purchased) {
                    Text(text = "BUY BOOK")
                } else {
                    Text(text = "Purchased")
                }
            }

            Text(text = "Description:", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            Text(text = uiState.bookData.description, fontSize = 14.sp)
        }
    }
}