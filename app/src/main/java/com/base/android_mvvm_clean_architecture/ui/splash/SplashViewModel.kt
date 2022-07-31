package com.base.android_mvvm_clean_architecture.ui.splash

import dagger.hilt.android.lifecycle.HiltViewModel
import com.base.android_mvvm_clean_architecture.common.base.BaseViewModel
import kr.enjoyworks.room.data.local.sharedpfers.AppPrefs
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val appPrefs: AppPrefs) : BaseViewModel() {

    fun isLogin() = appPrefs.userInfos?.isNotEmpty()
}
