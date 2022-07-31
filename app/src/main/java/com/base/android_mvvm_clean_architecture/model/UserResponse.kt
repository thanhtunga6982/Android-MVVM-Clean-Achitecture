package com.base.android_mvvm_clean_architecture.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int? = -1,
)