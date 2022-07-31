package com.base.android_mvvm_clean_architecture.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import com.base.android_mvvm_clean_architecture.common.base.BaseActivity
import com.base.android_mvvm_clean_architecture.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(LoginViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }
}