package com.base.android_mvvm_clean_architecture.model

import com.google.gson.annotations.SerializedName

data class LocationEntity(
    @SerializedName("title") var title: String? = "",
    @SerializedName("location_type") var locationType: String? = "",
    @SerializedName("woeid") var woeid: Long? = 0L,
    @SerializedName("latt_long") var lattLong: String? = "",
)
