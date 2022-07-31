package kr.enjoyworks.room.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.enjoyworks.room.data.local.dao.UserDao
import com.base.android_mvvm_clean_architecture.model.UserInfo

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
