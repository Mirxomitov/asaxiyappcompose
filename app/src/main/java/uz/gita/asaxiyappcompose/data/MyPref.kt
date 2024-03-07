package uz.gita.asaxiyappcompose.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPref @Inject constructor(private val pref: SharedPreferences) {
    fun getToken() : String =
        pref.getString("token", "").toString()

    fun setToken(token: String) =
        pref.edit().putString("token", token).apply()

    fun isFirstEnter() : Boolean {
        return pref.getBoolean("FIRST", true)
    }

    fun introFinished() {
        pref.edit().putBoolean("FIRST", false).apply()
    }

    fun removeToken() {
        pref.edit().remove("token").apply()
    }

}