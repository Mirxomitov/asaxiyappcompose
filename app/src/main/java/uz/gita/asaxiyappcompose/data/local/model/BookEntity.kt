package uz.gita.asaxiyappcompose.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "download_books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val bookUID: String,
    val path: String,
    val type: String
)