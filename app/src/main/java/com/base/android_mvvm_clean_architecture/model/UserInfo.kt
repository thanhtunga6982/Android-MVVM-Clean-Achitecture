package com.base.android_mvvm_clean_architecture.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserInfo(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "email") val email: String? = "",
    @ColumnInfo(name = "password") val password: String? = "",
    @ColumnInfo(name = "name") val name: String? = ""
)
