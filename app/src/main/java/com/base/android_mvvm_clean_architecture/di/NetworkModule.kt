package com.base.android_mvvm_clean_architecture.di

import android.annotation.SuppressLint
import android.app.Application
import androidx.viewbinding.BuildConfig
import com.base.android_mvvm_clean_architecture.BuildConfig.END_POINT
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.enjoyworks.room.data.local.sharedpfers.AppPrefs
import kr.enjoyworks.room.data.remote.api.ApiService
import kr.enjoyworks.room.data.remote.middleware.InterceptorImpl
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val READ_TIMEOUT: Long = 30
    private const val WRITE_TIMEOUT: Long = 30
    private const val CONNECTION_TIMEOUT: Long = 30

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideOkHttpCache(app: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        return Cache(app.cacheDir, cacheSize)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache,
        @Named("logging") logging: Interceptor,
        @Named("interceptor") interceptor: Interceptor,
        sslSocketFactory: SSLSocketFactory,
        trustAllCerts: X509TrustManager,
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.cache(cache)
        httpClientBuilder.addInterceptor(logging)
        httpClientBuilder.addInterceptor(interceptor)

        httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        httpClientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts)
        httpClientBuilder.hostnameVerifier { _, _ -> true }

        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    @Named("logging")
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    @Named("interceptor")
    fun provideInterceptor(appPrefs: AppPrefs): Interceptor {
        return InterceptorImpl(appPrefs)
    }

    @Singleton
    @Provides
    fun provideAppRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("")
            .client(okHttpClient)
            .build()


    /**
     * Instance of this interface manage which X509 certificates
     * may be used to authenticate the remote side of a secure socket.
     * Decisions may be based on trusted certificate authorities, certificate revocation lists,
     * online status checking or other means.
     */
    @SuppressLint("TrustAllX509TrustManager")
    @Singleton
    @Provides
    fun providerX509TrustManager(): X509TrustManager {
        // Create a trust manager that does not validate certificate chains
        return object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }

    @Singleton
    @Provides
    fun providerSslSocketFactory(trust: X509TrustManager): SSLSocketFactory {
        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf(trust), java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        return sslContext.socketFactory
    }
}
