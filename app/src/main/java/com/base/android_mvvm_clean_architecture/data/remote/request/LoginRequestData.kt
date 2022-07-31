package kr.enjoyworks.room.data.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequestData(
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
)