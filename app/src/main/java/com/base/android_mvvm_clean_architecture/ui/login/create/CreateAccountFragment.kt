package com.base.android_mvvm_clean_architecture.ui.login.create

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.base.android_mvvm_clean_architecture.R
import dagger.hilt.android.AndroidEntryPoint
import com.base.android_mvvm_clean_architecture.common.base.BaseFragment
import com.base.android_mvvm_clean_architecture.databinding.FragmentCreateAccountBinding
import com.base.android_mvvm_clean_architecture.extension.clickCoolDown
import com.base.android_mvvm_clean_architecture.model.UserInfo
import com.base.android_mvvm_clean_architecture.ui.dialog.DialogListener
import com.base.android_mvvm_clean_architecture.ui.dialog.showMessageDialog

@AndroidEntryPoint
class CreateAccountFragment :
    BaseFragment<FragmentCreateAccountBinding, CreateAccountViewModel>(CreateAccountViewModel::class) {

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentCreateAccountBinding {
        return FragmentCreateAccountBinding.inflate(inflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }

    override fun initAction() {
        super.initAction()
        bind.btnCreate.clickCoolDown {
            viewModelSelf.validationCreateAccount(
                UserInfo(
                    email = bind.edtEmail.text.toString(),
                    password = bind.edtPassword.text.toString(),
                    name = bind.edtName.text.toString()
                )
            )
        }
    }

    override fun onSubscribeObserver() {
        viewModelSelf.createErrorEvent.bindTo {
            bind.tvCreateError.text = it
        }

        viewModelSelf.createEvent.bindTo {
            showMessageDialog(message = "성공 만들기", dialogListener = object : DialogListener {
                override fun onPositive() {
                    super.onPositive()
                    findNavController().navigateUp()
                }
            })
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_create_account
}