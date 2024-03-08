package uz.gita.asaxiyappcompose.data.model

data class AudioBookData(
    val id : String ="",
    val author: String = "",
    val category: String = "",
    val description: String = "",
    val img: String = "",
    val name: String = "",
    val path: String = "",
    val size: String = "",
    val purchased : Boolean = false
)