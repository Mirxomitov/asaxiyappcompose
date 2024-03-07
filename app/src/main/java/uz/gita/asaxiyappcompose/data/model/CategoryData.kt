package uz.gita.asaxiyappcompose.data.model

import uz.gita.asaxiyappcompose.data.model.DataUI

data class CategoryData (
    val categoryName: String,
    val bookList : ArrayList<DataUI>
)