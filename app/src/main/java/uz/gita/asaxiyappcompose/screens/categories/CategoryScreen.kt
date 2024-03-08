package uz.gita.asaxiyappcompose.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel

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
                .weight(1f),
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(uiState.bookList) {
                ElevatedCard(elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier
                    .height(300.dp)
                    .width(200.dp)
                    .clickable {
                        eventDispatcher(
                            CategoryViewModel.CategoryIntent.OpenBook(
                                it.name, uiState.type
                            )
                        )
                    }
                ) {
                    Text(text = it.category)
                    Text(text = it.id)
                    Text(text = it.size)
                    Text(text = it.img)
                    Text(text = it.author)
                    Text(text = it.description)
                    Text(text = it.path)
                    Text(text = it.name)
                }
            }
        }
    }

}