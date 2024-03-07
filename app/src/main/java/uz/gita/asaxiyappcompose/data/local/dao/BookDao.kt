package uz.gita.asaxiyappcompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.gita.asaxiyappcompose.data.local.model.BookEntity

@Dao
interface BookDao {
    @Insert
    fun add(data: BookEntity)

    @Query("SELECT * FROM download_books WHERE bookUID = :bookUID LIMIT 1")
    fun get(bookUID: String) : List<BookEntity>

    @Query("DELETE from download_books")
    fun deleteAll()
}