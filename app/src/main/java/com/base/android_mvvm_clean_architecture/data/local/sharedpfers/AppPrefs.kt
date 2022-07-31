package kr.enjoyworks.room.data.local.sharedpfers

import android.content.SharedPreferences
import com.google.gson.Gson
import com.base.android_mvvm_clean_architecture.extension.arrays
import com.base.android_mvvm_clean_architecture.extension.objects
import com.base.android_mvvm_clean_architecture.extension.string
import com.base.android_mvvm_clean_architecture.model.UserInfo
import kotlin.reflect.KProperty

class AppPrefs(private val prefs: SharedPreferences, val gson: Gson) {

    var token by prefs.string("")
    var userInfo by prefs.objects(UserInfo::class.java, gson)
    var userInfos by prefs.arrays(arrayOf(UserInfo()).javaClass, gson)


    fun logout() {
        remove(::token)
    }

    fun remove(key: KProperty<*>) {
        prefs.edit().remove(key.name).apply()
    }
}
