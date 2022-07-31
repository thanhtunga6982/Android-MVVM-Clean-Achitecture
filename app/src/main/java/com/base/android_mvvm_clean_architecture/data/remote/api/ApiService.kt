package kr.enjoyworks.room.data.remote.api

import kr.enjoyworks.room.data.remote.request.LoginRequestData
import kr.enjoyworks.room.data.remote.response.BaseApiResponse
import kr.enjoyworks.room.data.remote.response.ResponseData
import com.base.android_mvvm_clean_architecture.model.LocationEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/account/login")
    suspend fun login(@Body body: LoginRequestData): BaseApiResponse<ResponseData>

    @GET("edf7e2a7-c927-4ec9-86da-adba791bc9fa")
    suspend fun getInfoLocation(): List<LocationEntity>
}
