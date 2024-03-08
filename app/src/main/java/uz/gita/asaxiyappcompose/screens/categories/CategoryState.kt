package uz.gita.asaxiyappcompose.screens.categories

import uz.gita.asaxiyappcompose.data.model.AudioBookData

data class CategoryState (
    var categoryName : String = "",
    var type : String = "",
    var bookList : List<AudioBookData> = listOf()
)