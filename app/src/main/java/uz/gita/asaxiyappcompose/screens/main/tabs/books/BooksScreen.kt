package uz.gita.asaxiyappcompose.screens.main.tabs.books

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import uz.gita.asaxiyappcompose.R
import uz.gita.asaxiyappcompose.ui.theme.AsaxiyAppComposeTheme

class BooksScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: BooksViewModel = getViewModel<BooksViewModelImpl>()
        BooksContent(viewModel.uiState.collectAsState().value, viewModel::onEventDispatchers)
    }
}

@Composable
fun BooksContent(uiState: BooksState, eventDispatchers: (BooksViewModel.BooksIntent) -> Unit) {
    eventDispatchers(BooksViewModel.BooksIntent.ShowBooks)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(uiState.allCategoryBooksList) { categoryData ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = categoryData.categoryName)
                Text(text = "All books")
            }
            LazyRow {
                items(categoryData.bookList) {
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

@Preview(showBackground = true)
@Composable
fun BooksPreview() {
    AsaxiyAppComposeTheme {
        BooksContent(eventDispatchers = {}, uiState = BooksState())
    }
}

//private val ls = arrayListOf<List<CategoryData>>().apply {
//    repeat(10) {
//        add(
//            listOf(
//                CategoryData(
//                    categoryName = "name",
//                    bookList = arrayListOf(
//                        DataUI(
//                            id = "1",
//                            author = "Author Name",
//                            category = "Humor",
//                            description = "Book lorem ispum",
//                            img = "https://firebasestorage.googleapis.com/v0/b/asaxiy-books.appspot.com/o/images%2Faudio_book.png?alt=media&token=87cd9e33-ce3f-41ab-9fab-7eacb1d24181",
//                            name = "Audio Name",
//                            path = "audio/Ж_Бизе,_Кармен,_Куплеты_Эскамильо,_Георг_Отс.mp3",
//                            size = "5904718",
//                            purchased = true
//                        ),
//                        DataUI(
//                            id = "2",
//                            author = "Author Name",
//                            category = "Humor",
//                            description = "Book lorem ispum",
//                            img = "https://firebasestorage.googleapis.com/v0/b/asaxiy-books.appspot.com/o/images%2Faudio_book.png?alt=media&token=87cd9e33-ce3f-41ab-9fab-7eacb1d24181",
//                            name = "Audio Name",
//                            path = "audio/Ж_Бизе,_Кармен,_Куплеты_Эскамильо,_Георг_Отс.mp3",
//                            size = "5904718",
//                            purchased = false
//                        ),
//                        DataUI(
//                            id = "3",
//                            author = "Author Name",
//                            category = "Humor",
//                            description = "Book lorem ispum",
//                            img = "https://firebasestorage.googleapis.com/v0/b/asaxiy-books.appspot.com/o/images%2Faudio_book.png?alt=media&token=87cd9e33-ce3f-41ab-9fab-7eacb1d24181",
//                            name = "Audio Name",
//                            path = "audio/Ж_Бизе,_Кармен,_Куплеты_Эскамильо,_Георг_Отс.mp3",
//                            size = "5904718",
//                            purchased = false
//                        )
//                    )
//                )
//            )
//        )
//    }
//}