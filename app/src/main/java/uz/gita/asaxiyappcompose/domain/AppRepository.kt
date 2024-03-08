package uz.gita.asaxiyappcompose.domain

import kotlinx.coroutines.flow.Flow
import uz.gita.asaxiyappcompose.data.model.AudioBookData
import uz.gita.asaxiyappcompose.data.model.AudioCategoryData
import uz.gita.asaxiyappcompose.data.model.CategoryData
import uz.gita.asaxiyappcompose.data.model.UserBookData
import uz.gita.asaxiyappcompose.data.model.UserData


interface AppRepository {
    var currentCategory: String
    var currentType: String
    var currentBookName: String

    fun getBooksByCategoryMap(): Flow<Result<List<CategoryData>>>
    fun loadCategories(): Flow<Result<List<AudioCategoryData>>>
    fun loadAudios(category: String, type: String): Flow<Result<List<AudioBookData>>>
    fun getAudioBookDetails(bookName: String, type: String): Flow<Result<AudioBookData>>

    fun login(email: String, password: String): Flow<Result<Unit>>
    fun register(userData: UserData, password: String): Flow<Result<Unit>>
    fun getUserDataByToken(): Flow<Result<UserData>>

    fun getToken(): String
    fun isFirstEnter(): Boolean
    fun introFinished()
    fun buyBook(bookName: String, type: String): Flow<Result<Unit>>
    fun allUserBooks(): Flow<Result<List<UserBookData>>>
    fun download(data: UserBookData): Flow<Result<UserBookData>>

    fun deleteExistDataFromLocal()
}