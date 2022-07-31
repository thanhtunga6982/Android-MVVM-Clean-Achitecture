package com.base.android_mvvm_clean_architecture.ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import com.base.android_mvvm_clean_architecture.common.base.BaseViewModel
import kr.enjoyworks.room.common.utils.SingleLiveEvent
import kr.enjoyworks.room.data.DataResult
import kr.enjoyworks.room.data.local.AppDatabase
import kr.enjoyworks.room.data.local.sharedpfers.AppPrefs
import com.base.android_mvvm_clean_architecture.model.LocationEntity
import com.base.android_mvvm_clean_architecture.repository.LocationRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var locationRepository: LocationRepository,
    private val appDatabase: AppDatabase,
    private val appPrefs: AppPrefs
) : BaseViewModel() {
    val listLocationEvent = SingleLiveEvent<List<LocationEntity>>()
    fun getLocationInfo() {
        launchDataLoad {
            when (val response = locationRepository.getLocation()) {
                is DataResult.Success -> {
                    response.data?.let {
                        listLocationEvent.value = it
                    }
                }
                is DataResult.Error -> setError(response.error)
            }
        }
    }
}