package kr.enjoyworks.room.di

import android.app.Application
import android.content.res.Resources
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kr.enjoyworks.room.data.remote.middleware.BooleanAdapter
import kr.enjoyworks.room.data.remote.middleware.DoubleAdapter
import kr.enjoyworks.room.data.remote.middleware.IntegerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        val booleanAdapter = BooleanAdapter()
        val integerAdapter = IntegerAdapter()
        val doubleAdapter = DoubleAdapter()
        return GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Boolean::class.java, booleanAdapter)
            .registerTypeAdapter(Int::class.java, integerAdapter)
            .registerTypeAdapter(Double::class.java, doubleAdapter)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    @Singleton
    @Provides
    fun provideResources(app: Application): Resources {
        return app.resources
    }
}
