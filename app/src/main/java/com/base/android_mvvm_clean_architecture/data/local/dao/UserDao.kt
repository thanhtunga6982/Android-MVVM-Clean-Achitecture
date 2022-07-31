package kr.enjoyworks.room.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.base.android_mvvm_clean_architecture.model.UserInfo

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg user: UserInfo?)

    @Query("SELECT * FROM users")
    fun loadAll(): List<UserInfo>?
}