package com.base.android_mvvm_clean_architecture.ui

import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import com.base.android_mvvm_clean_architecture.common.base.BaseActivity
import com.base.android_mvvm_clean_architecture.databinding.ActivityMainBinding
import kr.enjoyworks.room.ui.MainViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initializeAction() {
        super.initializeAction()
    }
}