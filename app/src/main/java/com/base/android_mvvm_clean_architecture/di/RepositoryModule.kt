package com.base.android_mvvm_clean_architecture.di

import com.base.android_mvvm_clean_architecture.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.enjoyworks.room.data.remote.api.ApiService
import com.base.android_mvvm_clean_architecture.repository.LocationRepository
import com.base.android_mvvm_clean_architecture.repository.LocationRepositoryImpl
import com.base.android_mvvm_clean_architecture.repository.UserRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun bindUserRepository(service: ApiService): UserRepository {
        return UserRepositoryImpl(service)
    }

    @Singleton
    @Provides
    fun bindLocationRepository(service: ApiService): LocationRepository {
        return LocationRepositoryImpl(service)
    }
}
