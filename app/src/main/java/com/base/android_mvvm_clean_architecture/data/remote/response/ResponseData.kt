package kr.enjoyworks.room.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("message")
    var message: String
)