package com.base.android_mvvm_clean_architecture.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.base.android_mvvm_clean_architecture.R
import dagger.hilt.android.AndroidEntryPoint
import com.base.android_mvvm_clean_architecture.common.base.BaseFragment
import com.base.android_mvvm_clean_architecture.databinding.FragmentLoginBinding
import com.base.android_mvvm_clean_architecture.extension.clickCoolDown
import com.base.android_mvvm_clean_architecture.extension.finishSlideOut
import com.base.android_mvvm_clean_architecture.extension.launchActivity
import com.base.android_mvvm_clean_architecture.extension.visibleElseGone
import com.base.android_mvvm_clean_architecture.ui.dialog.showMessageDialog
import com.base.android_mvvm_clean_architecture.ui.MainActivity

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(LoginViewModel::class) {

    override val layoutId = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        hideActionBar()
        viewModelSelf.saveDataUser()

    }

    override fun inflateViewBinding(inflater: LayoutInflater): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater)
    }

    override fun initAction() {
        super.initAction()
        bind.btnLogin.clickCoolDown {
            viewModelSelf.validationLogin(
                bind.edtEmail.text.toString(),
                bind.edtPassword.text.toString()
            )
        }
        bind.tvCreateAccount.clickCoolDown {
            findNavController().navigate(R.id.create_account_dest)
        }
    }

    override fun onSubscribeObserver() {
        super.onSubscribeObserver()
        viewModelSelf.emailError.bindTo {
            bind.tvEmailError.text = it
            bind.tvEmailError.visibleElseGone { it.isNotEmpty() }
        }
        viewModelSelf.passwordError.bindTo {
            bind.tvPasswordError.text = it
            bind.tvPasswordError.visibleElseGone { it.isNotEmpty() }
        }
        viewModelSelf.validLoginSuccessEvent.bindTo { isSuccess ->
            if (isSuccess) {
                activity?.launchActivity<MainActivity> {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                baseActivity.finishSlideOut()
            } else {
                showMessageDialog(message = "Email or password incorrect")
            }
        }
    }
}
