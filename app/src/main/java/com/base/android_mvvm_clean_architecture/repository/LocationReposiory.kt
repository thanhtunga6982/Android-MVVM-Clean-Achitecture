package com.base.android_mvvm_clean_architecture.repository

import kr.enjoyworks.room.data.DataResult
import kr.enjoyworks.room.data.remote.api.ApiService
import com.base.android_mvvm_clean_architecture.model.LocationEntity

interface LocationRepository {
    suspend fun getLocation(): DataResult<List<LocationEntity>>
}

class LocationRepositoryImpl(private val appServerService: ApiService) : BaseRepository(),
    LocationRepository {
    override suspend fun getLocation(): DataResult<List<LocationEntity>> {
        return callRequest { originResponse(appServerService.getInfoLocation()) }
    }
}
