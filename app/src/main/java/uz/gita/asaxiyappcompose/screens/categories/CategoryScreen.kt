package uz.gita.asaxiyappcompose.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

class CategoryScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: CategoryViewModel = getViewModel<CategoryViewModelImpl>()
        CategoryContent(
            viewModel.uiState.collectAsState().value, viewModel::onEventDispatchers
        )
    }
}

@Composable
fun CategoryContent(uiState: CategoryState, eventDispatcher: (CategoryViewModel.CategoryIntent) -> Unit) {
    eventDispatcher(CategoryViewModel.CategoryIntent.ShowBooksByCategory(uiState.categoryName, uiState.type))
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.Black),
        ) {
            Text(text = "Category", fontSize = 18.sp, color = Color.White)
        }

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(uiState.bookList) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier
                        .weight(1f)
                        .width(200.dp)
                        .clickable {
                            eventDispatcher(
                                CategoryViewModel.CategoryIntent.OpenBook(
                                    it.name, uiState.type
                                )
                            )
                        }
                        .background(Color.White)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    AsyncImage(
                        model = it.img,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.audio_book),
                        error = painterResource(id = R.drawable.audio_book),
                        contentDescription = ""
                    )
                    Text(
                        text = it.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(text = it.author, fontSize = 12.sp)
                }
            }
        }
    }

}