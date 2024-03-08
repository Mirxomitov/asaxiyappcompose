package uz.gita.asaxiyappcompose.screens.main.tabs.books

import uz.gita.asaxiyappcompose.data.model.CategoryData

data class BooksState(
    var allCategoryBooksList: List<CategoryData> = listOf()
)