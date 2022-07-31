package kr.enjoyworks.room.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseApiResponse<T>(
    @SerializedName("statusCode") var statusCode: Int,
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: String,
    @SerializedName("responseData") var responseData: T? = null
)
