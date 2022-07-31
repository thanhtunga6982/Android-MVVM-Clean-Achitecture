package kr.enjoyworks.room.data.remote.middleware

import androidx.annotation.NonNull
import kr.enjoyworks.room.data.local.sharedpfers.AppPrefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class InterceptorImpl(private val appPrefs: AppPrefs) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        val builder = initializeHeader(chain)
        val request = builder.build()

        return chain.proceed(request)
    }

    private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
        val request = chain.request()
        return request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("charset", "utf-8")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Cache-Control", "no-store")
            .method(request.method, request.body)
    }

    companion object {
        private const val TOKEN_TYPE = "Bearer "
        private const val KEY_TOKEN = "Authorization"
    }
}
