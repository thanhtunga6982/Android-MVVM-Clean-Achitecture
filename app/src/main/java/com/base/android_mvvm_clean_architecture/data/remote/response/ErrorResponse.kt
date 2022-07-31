package kr.enjoyworks.room.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code") var code: Int = 0,
    @SerializedName("status_code") var statusCode: Int = 0,
    @SerializedName("message") var message: String = "",
    @SerializedName("errors") var errors: List<ErrorReason>?,
)

data class ErrorReason(
    @SerializedName("name") val name: String?,
    @SerializedName("reason") val reason: String?,
)
