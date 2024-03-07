package uz.gita.asaxiyappcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.asaxiyappcompose.data.local.dao.BookDao
import uz.gita.asaxiyappcompose.data.local.model.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class AppDB : RoomDatabase() {
    abstract fun getBookDao() : BookDao
}