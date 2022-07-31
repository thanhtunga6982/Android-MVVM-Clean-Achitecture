package com.base.android_mvvm_clean_architecture.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.enjoyworks.room.data.local.sharedpfers.AppPrefs
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Singleton
    @Provides
    fun providerSharedPrefs(app: Application): SharedPreferences {
        return app.getSharedPreferences(
            "", Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun providerAppPrefs(prefs: SharedPreferences, gson: Gson): AppPrefs {
        return AppPrefs(prefs, gson)
    }
}
