package uz.gita.asaxiyappcompose.data.model

data class UserBookData(
    val bookUID: String,
    val author: String,
    val category: String,
    val description: String,
    val img: String,
    val name: String,
    val path: String,
    val size: String,
    val type: String,
    var download : Boolean
)