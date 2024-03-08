package uz.gita.asaxiyappcompose.domain

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import uz.gita.asaxiyappcompose.data.MyPref
import uz.gita.asaxiyappcompose.data.local.dao.BookDao
import uz.gita.asaxiyappcompose.data.local.model.BookEntity
import uz.gita.asaxiyappcompose.data.model.AudioBookData
import uz.gita.asaxiyappcompose.data.model.AudioCategoryData
import uz.gita.asaxiyappcompose.data.model.CategoryData
import uz.gita.asaxiyappcompose.data.model.DataUI
import uz.gita.asaxiyappcompose.data.model.UserBookData
import uz.gita.asaxiyappcompose.data.model.UserBookRequest
import uz.gita.asaxiyappcompose.data.model.UserData
import uz.gita.asaxiyappcompose.data.model.UserRequest
import uz.gita.asaxiyappcompose.utils.logger
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImp @Inject constructor(
    private val pref: MyPref,
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val bookDao: BookDao
) : AppRepository {

    private var listHome: List<UserBookData> = listOf()


    override var currentBook: UserBookData = UserBookData()
        set(value) {
            logger("currentBook SETTER :$value")
            field = value
        }
    override var currentCategory: String = ""
    override var currentType: String = ""
        set(value) {
            logger("currentTYPE setter =$value")
            if (value != "") field = value
        }
    override var currentBookName: String = ""

    override fun getBooksByCategoryMap(): Flow<Result<List<CategoryData>>> = callbackFlow {
        fireStore.collection("books")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val categoryDataList = mutableListOf<CategoryData>()
                val booksByCategoryMap = HashMap<String, ArrayList<DataUI>>()

                querySnapshot.documents.forEach { document ->
                    val dataUI = document.toObject(DataUI::class.java)

                    val category = dataUI?.category ?: return@forEach

                    val categoryList = booksByCategoryMap.getOrPut(category) { ArrayList() }
                    dataUI.let { categoryList.add(it) }
                }

                booksByCategoryMap.forEach { (categoryName, bookList) ->
                    categoryDataList.add(CategoryData(categoryName, bookList))
                }

                trySend(Result.success(categoryDataList))
                close()
            }
            .addOnFailureListener { error ->

                trySend(Result.failure(error))
                close()
            }

        awaitClose()
    }

    @SuppressLint("NewApi")
    override fun loadCategories(): Flow<Result<List<AudioCategoryData>>> = callbackFlow {

        fireStore.collection("audios")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val map = HashMap<String, ArrayList<AudioBookData>>()
                val ls = arrayListOf<AudioCategoryData>()

                querySnapshot.forEach {
                    val category = it.data["category"].toString()

                    if (!map.containsKey(category)) {
                        map[category] = arrayListOf()
                    }

                    fireStore.collection("userBooks").whereEqualTo("bookId", it.id)
                        .get()
                        .addOnSuccessListener {

                        }
                        .addOnFailureListener {
                            trySend(Result.failure(it))
                        }

                    val data = AudioBookData(
                        id = it.id,
                        author = it.data.getOrDefault("author", "").toString(),
                        category = it.data.getOrDefault("category", "").toString(),
                        description = it.data.getOrDefault("description", "").toString(),
                        img = it.data.getOrDefault("img", "").toString(),
                        name = it.data.getOrDefault("name", "").toString(),
                        path = it.data.getOrDefault("path", "").toString(),
                        size = it.data.getOrDefault("size", "0").toString(),
                        purchased = false,
                    )

                    map[category]!!.add(data)
                }

                map.keys.forEach {
                    val list = map[it]
                    ls.add(AudioCategoryData(it, list!!))
                }

                trySend(Result.success(ls))
                close()
            }
            .addOnFailureListener { error ->
                trySend(Result.failure(error))
                close()
            }

        awaitClose()
    }

    @SuppressLint("NewApi")
    override fun loadAudios(category: String, type: String): Flow<Result<List<AudioBookData>>> = callbackFlow {

        fireStore
            .collection(type)
            .whereEqualTo("category", category)
            .addSnapshotListener { snapshot, error ->
                val ls = arrayListOf<AudioBookData>()
                when {
                    snapshot != null -> {
                        snapshot.forEach {
                            val data = AudioBookData(
                                id = it.id,
                                it.data.getOrDefault("author", "").toString(),
                                it.data.getOrDefault("category", "").toString(),
                                it.data.getOrDefault("description", "").toString(),
                                it.data.getOrDefault("img", "").toString(),
                                it.data.getOrDefault("name", "").toString(),
                                it.data.getOrDefault("path", "").toString(),
                                it.data.getOrDefault("size", "0").toString(),
                                it.data.getOrDefault("purchased", "false").toString().toBoolean()
                            )

                            ls.add(data)
                        }

                        trySend(Result.success(ls))
                    }

                    error != null -> {
                        trySend(Result.failure(error))
                    }
                }
            }

        awaitClose()
    }

    @SuppressLint("SuspiciousIndentation", "NewApi")
    override fun getAudioBookDetails(bookName: String, type: String): Flow<Result<AudioBookData>> = callbackFlow {

        fireStore
            .collection(type)
            .whereEqualTo("name", bookName)
            .limit(1)
            .addSnapshotListener { value, error ->
                when {
                    value != null -> {
                        value.forEach {

                            if (listHome.isNotEmpty()) {
                                listHome.forEach { user ->
                                    if (user.bookUID == it.id) {
                                        val data = AudioBookData(
                                            id = it.id,
                                            it.data.getOrDefault("author", "").toString(),
                                            it.data.getOrDefault("category", "").toString(),
                                            it.data.getOrDefault("description", "").toString(),
                                            it.data.getOrDefault("img", "").toString(),
                                            it.data.getOrDefault("name", "").toString(),
                                            it.data.getOrDefault("path", "").toString(),
                                            it.data.getOrDefault("size", "0").toString(),
                                            true
                                        )

                                        trySend(Result.success(data))
                                    } else {
                                        val data = AudioBookData(
                                            id = it.id,
                                            it.data.getOrDefault("author", "").toString(),
                                            it.data.getOrDefault("category", "").toString(),
                                            it.data.getOrDefault("description", "").toString(),
                                            it.data.getOrDefault("img", "").toString(),
                                            it.data.getOrDefault("name", "").toString(),
                                            it.data.getOrDefault("path", "").toString(),
                                            it.data.getOrDefault("size", "0").toString(),
                                            false
                                        )

                                        trySend(Result.success(data))
                                    }
                                }
                            }

                        }
                    }

                    error != null -> {
                        trySend(Result.failure(error))
                    }
                }
            }

        awaitClose()
    }

    override fun login(email: String, password: String): Flow<Result<Unit>> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userUid = user?.uid
                pref.setToken(userUid.toString())
                trySend(Result.success(Unit))
            } else {
                trySend(Result.failure(Exception("Login")))
            }
        }
        awaitClose()
    }

    override fun register(userData: UserData, password: String): Flow<Result<Unit>> = callbackFlow {
        auth.createUserWithEmailAndPassword(userData.gmail, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userUid = user?.uid
                pref.setToken(userUid.toString())
                fireStore.collection("users")
                    .add(UserRequest(userData.firstName, userData.lastName, userData.phone, userData.gmail, userUid.toString()))
                    .addOnSuccessListener {
                        trySend(Result.success(Unit))
                    }
                    .addOnFailureListener {
                        trySend(Result.failure(it))
                    }
            } else {
                trySend(Result.failure(Exception("Login")))
            }
        }
        awaitClose()
    }

    override fun getUserDataByToken(): Flow<Result<UserData>> = callbackFlow {
        val token = pref.getToken()

        fireStore.collection("users")
            .whereEqualTo("id", token)
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach {
                    val firstName = it.getString("firstName") ?: ""
                    val lastName = it.getString("lastName") ?: ""
                    val phone = it.getString("phone") ?: ""
                    val gmail = it.getString("gmail") ?: ""
                    val userData = UserData(
                        firstName = firstName,
                        lastName = lastName,
                        phone = phone,
                        gmail = gmail
                    )
                    trySend(Result.success(userData))
                }
            }

            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()

    }

    override fun getToken(): String =
        pref.getToken()

    override fun isFirstEnter(): Boolean = pref.isFirstEnter()

    override fun introFinished() {
        pref.introFinished()
    }

    override fun buyBook(bookName: String, type: String): Flow<Result<Unit>> = channelFlow {
        logger("REPOSITORY BUY BOOK -> TYPE=$type")
        fireStore
            .collection(type)
            .whereEqualTo("name", bookName)
            .limit(1)
            .get()
            .addOnSuccessListener {
                var bookId = ""

                it.forEach {
                    bookId = it.id
                }

                fireStore
                    .collection("userBooks")
                    .add(
                        UserBookRequest(
                            pref.getToken(),
                            bookId,
                            type
                        )
                    )

                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun allUserBooks(): Flow<Result<List<UserBookData>>> = channelFlow {
        val ls = arrayListOf<UserBookData>()

        try {
            val userBooksSnapshot = fireStore
                .collection("userBooks")
                .whereEqualTo("userId", pref.getToken())
                .get()
                .await() // Wait for the outer request to complete

            for (document in userBooksSnapshot) {
                val bookId = document.getString("bookId") ?: continue
                val type = document.getString("bookType") ?: continue
                val daoData = bookDao.get(bookId)

                val bookDataSnapshot = fireStore
                    .collection(type)
                    .document(bookId)
                    .get()
                    .await() // Wait for the inner request to complete

                val data = bookDataSnapshot.data ?: continue

                val path = if (daoData.isNotEmpty()) daoData[0].path else data["path"].toString()

                val userBookData = UserBookData(
                    bookUID = bookId,
                    author = data["author"].toString(),
                    category = data["category"].toString(),
                    description = data["description"].toString(),
                    img = data["img"].toString(),
                    name = data["name"].toString(),
                    path = path,
                    size = data["size"].toString(),
                    type = type,
                    download = daoData.isNotEmpty()
                )
                ls.add(userBookData)
            }

            listHome = ls

            trySend(Result.success(ls))
        } catch (e: Exception) {
            trySend(Result.failure(e))
        }

        awaitClose()
    }

    override fun download(data: UserBookData): Flow<Result<UserBookData>> = callbackFlow<Result<UserBookData>> {
        val book = if (data.type == "books") File.createTempFile("books", ".pdf") else File.createTempFile("audios", ".mp3")

        storage.getReferenceFromUrl(storage.reference.toString() + data.path)
            .getFile(book)
            .addOnSuccessListener {
                bookDao.add(
                    BookEntity(
                        id = 0,
                        bookUID = data.bookUID,
                        path = book.absolutePath,
                        type = data.type
                    )
                )
                data.download = true
                trySend(Result.success(data))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
            .addOnProgressListener {
                logger("yuklanyapti ${it.bytesTransferred * 100 / it.totalByteCount} by ${it.bytesTransferred} kb ${it.totalByteCount}")
            }
        awaitClose()
    }.flowOn(Dispatchers.IO)


    override fun deleteExistDataFromLocal() {
        return bookDao.deleteAll()
    }

}
