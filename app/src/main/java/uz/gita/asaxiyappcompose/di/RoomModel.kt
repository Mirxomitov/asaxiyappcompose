package uz.gita.asaxiyappcompose.di

import uz.gita.asaxiyappcompose.data.AppDB
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.asaxiyappcompose.data.local.dao.BookDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModel {
    @[Provides Singleton]
    fun provideDB(@ApplicationContext context: Context) : AppDB = Room
        .databaseBuilder(context, AppDB::class.java, "Books.db")
            .allowMainThreadQueries()
            .build()

    @[Provides Singleton]
    fun provideBookDao(db: AppDB) : BookDao = db.getBookDao()
}