package com.base.android_mvvm_clean_architecture.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.enjoyworks.room.data.DataResult
import kr.enjoyworks.room.data.remote.response.BaseApiResponse

abstract class BaseRepository {

    suspend fun <T : Any> callRequest(request: suspend () -> DataResult<T>): DataResult<T> {
        return try {
            request()
        } catch (error: Throwable) {
            error.printStackTrace()
            DataResult.Error(error)
        }
    }

    suspend fun <T : Any> originResponse(response: T): DataResult<T> {
        return withContext(Dispatchers.IO) {
            DataResult.Success(response)
        }
    }

    suspend fun <T : Any> handleResponse(response: BaseApiResponse<T>): DataResult<T> {
        return withContext(Dispatchers.IO) {
            DataResult.Success(response.responseData)
        }
    }
}
