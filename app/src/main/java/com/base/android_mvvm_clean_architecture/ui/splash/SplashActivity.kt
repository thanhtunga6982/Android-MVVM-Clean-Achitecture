package com.base.android_mvvm_clean_architecture.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.base.android_mvvm_clean_architecture.common.base.BaseActivity
import com.base.android_mvvm_clean_architecture.databinding.ActivitySplashBinding
import com.base.android_mvvm_clean_architecture.extension.finishSlideOut
import com.base.android_mvvm_clean_architecture.extension.launchActivity
import com.base.android_mvvm_clean_architecture.ui.MainActivity
import com.base.android_mvvm_clean_architecture.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity() :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(SplashViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        lifecycleScope.launch {
            delay(2000)
            if (viewModelSelf.isLogin() == true) launchActivity<MainActivity> { }
            else launchActivity<LoginActivity> { }
            this@SplashActivity.finishSlideOut()
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
    }
}
