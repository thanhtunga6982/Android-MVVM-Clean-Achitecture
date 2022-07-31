package com.base.android_mvvm_clean_architecture.repository

import kr.enjoyworks.room.data.DataResult
import kr.enjoyworks.room.data.remote.api.ApiService
import kr.enjoyworks.room.data.remote.request.LoginRequestData
import kr.enjoyworks.room.data.remote.response.ResponseData
import javax.inject.Inject

interface UserRepository {
    suspend fun login(request: LoginRequestData): DataResult<ResponseData>
}

class UserRepositoryImpl @Inject constructor(
    private val appServerService: ApiService,
) : UserRepository, BaseRepository() {

    override suspend fun login(request: LoginRequestData): DataResult<ResponseData> {
        return callRequest { handleResponse(appServerService.login(request)) }
    }
}
