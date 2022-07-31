package com.base.android_mvvm_clean_architecture.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.zuluft.impl.SafeFragmentTransactorActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.base.android_mvvm_clean_architecture.extension.setTransparentStatusBar
import com.base.android_mvvm_clean_architecture.ui.dialog.showMessageDialog
import kr.enjoyworks.room.ui.dialog.LoadingDialog
import kotlin.reflect.KClass

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel>(classVM: KClass<VM>) :
    SafeFragmentTransactorActivity() {
    protected lateinit var bind: VB
    protected val viewModelSelf by ViewModelLazy(classVM, { viewModelStore }, { defaultViewModelProviderFactory })
    private val loading by lazy { LoadingDialog(this).apply { setCancelable(false) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setTransparentStatusBar()
        bind = inflateViewBinding(layoutInflater)
        setContentView(bind.root)
        initView(savedInstanceState)
    }

    open fun onSubscribeObserver() {
        bindViewModel(viewModelSelf)
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    open fun initView(savedInstanceState: Bundle?) {}

    open fun bindViewModel(viewModel: BaseViewModel) {}

    open fun handleError(exception: Throwable) {
        //TODO handle error
    }

    open fun initializeAction() {}


    open fun showLoading() {
        lifecycleScope.launch(Dispatchers.Main) {
            loading.apply { if (!isShowing) show() }
        }
    }

    open fun hideLoading() {
        lifecycleScope.launch(Dispatchers.Main) {
            loading.dismiss()
        }
    }

    open fun observeBaseViewModel() {
        viewModelSelf.isLoading.observe(this) { isShow ->
            if (isShow) showLoading() else hideLoading()
        }
        viewModelSelf.errorMsgId.observe(this) { msgId ->
            showMessageDialog(message = getString(msgId))
        }
        viewModelSelf.errorMessage.observe(this) { msg ->
            showMessageDialog(message = msg)
        }
        viewModelSelf.tokenExpire.observe(this) {
            //Logout
        }
    }

    protected fun setTitle(title: String) {
        supportActionBar?.run {
            this.title = title
            show()
        }
    }

    protected inline infix fun <T> LiveData<T>.bindTo(crossinline action: (T?) -> Unit) =
        this.observe(this@BaseActivity, { action(it) })
}