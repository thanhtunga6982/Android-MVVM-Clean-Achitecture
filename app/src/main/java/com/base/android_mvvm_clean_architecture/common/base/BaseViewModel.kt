package com.base.android_mvvm_clean_architecture.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.android_mvvm_clean_architecture.R
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kr.enjoyworks.room.common.utils.SingleLiveEvent
import kr.enjoyworks.room.data.remote.response.ErrorResponse
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseViewModel() : ViewModel() {
    val isLoading by lazy { SingleLiveEvent<Boolean>() }
    val errorMsgId by lazy { SingleLiveEvent<Int>() }
    val errorMessage by lazy { SingleLiveEvent<String>() }
    var errorCode: Int? = null
    val tokenExpire by lazy { SingleLiveEvent<Unit>() }
    var firstLoading = true
    val isRefresh by lazy { SingleLiveEvent<Boolean>().apply { value = false } }

    @Inject
    lateinit var gson: Gson

    open fun setError(error: Throwable, handleMessage: (() -> String?)? = null) {
        isLoading.value = false
        handleApiError(error, handleMessage)
    }

    fun launchDataLoad(
        hasLoading: Boolean = firstLoading,
        hideLoading: Boolean = firstLoading,
        requestBlock: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch {
            if (hasLoading) {
                isLoading.value = isRefresh.value == false
                requestBlock()
                if (hideLoading) isLoading.value = false
            } else {
                requestBlock()
            }
            isRefresh.value = false
        }
    }

    private fun handleApiError(apiError: Throwable, handleMessage: (() -> String?)? = null) {
        when (apiError) {
            is HttpException -> handleErrorMessage(apiError, handleMessage)
            is SocketTimeoutException -> errorMsgId.value = R.string.msg_error_time_out
            is UnknownHostException, is IOException -> errorMsgId.value =
                R.string.msg_error_no_internet
            else -> errorMsgId.value = R.string.msg_error_data_parse
        }
    }

    private fun handleErrorMessage(
        e: Throwable,
        handleMessage: (() -> String?)? = null,
        errorKey: String? = null
    ) {
        val responseBody = (e as HttpException).response()?.errorBody()
        errorCode = e.response()?.code()
        if (errorCode == 401) {
            tokenExpire.call()
            return
        }
        responseBody?.let { body ->
            try {
                val jsonObject = JSONObject(body.string())
                val response = gson.fromJson(jsonObject.toString(), ErrorResponse::class.java)
                val resId = when (response.message) {
                    "http_internal_error" -> R.string.msg_error_data_parse
                    else -> 0
                }
                if (resId != 0) {
                    errorMsgId.value = resId
                } else {
                    var msg = response.message
                    if (errorKey != null) {
                        val reason = response.errors?.firstOrNull { it.name == errorKey }
                        reason?.let { msg = it.reason ?: "" }
                    }
                    errorMessage.value = handleMessage?.invoke() ?: msg
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                errorMsgId.value = R.string.msg_error_data_parse
            }
        } ?: kotlin.run {
            errorMsgId.value = R.string.msg_error_data_parse
        }
    }
}