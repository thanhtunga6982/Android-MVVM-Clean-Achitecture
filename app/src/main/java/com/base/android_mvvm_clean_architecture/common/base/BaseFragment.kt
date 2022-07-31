package com.base.android_mvvm_clean_architecture.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.base.android_mvvm_clean_architecture.R
import com.base.android_mvvm_clean_architecture.ui.dialog.showMessageDialog
import com.zuluft.impl.SafeFragmentTransactorFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.enjoyworks.room.common.utils.Dialog
import kr.enjoyworks.room.ui.dialog.LoadingDialog
import kotlin.reflect.KClass

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel>(classVM: KClass<VM>) :
    SafeFragmentTransactorFragment() {

    private var _binding: VB? = null

    protected val bind get() = _binding!!
    protected val baseActivity by lazy { requireActivity() as BaseActivity<*, *> }
    private val loading by lazy { LoadingDialog(requireContext()).apply { setCancelable(false) } }

    protected abstract val layoutId: Int
    protected val viewModelSelf by createViewModelLazy(classVM, { viewModelStore })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = inflateViewBinding(inflater)

        initView(savedInstanceState)
        initAction()
        onSubscribeObserver()
        return bind.root
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater): VB

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

    open fun onSubscribeObserver() {
        bindViewModel(viewModelSelf)
    }

    protected open fun initView(savedInstanceState: Bundle?) {}

    protected open fun initAction() {}

    protected open fun bindViewModel(viewModel: BaseViewModel) {
        viewModel.isLoading bindTo { isShow ->
            if (isShow) showLoading() else hideLoading()
        }
        viewModel.errorMsgId bindTo {
            showMessageDialog(message = getString(it))
        }
        viewModel.errorMessage bindTo {
            showMessageDialog(message = it)
        }
        viewModel.tokenExpire.bindTo {
            //logout
        }
    }

    protected fun showErrorDialog(message: String) {
        context?.run {
            Dialog.createOneButtonDialog(
                this,
                getString(R.string.error),
                message,
                getString(R.string.ok)
            ) { dialog, _ -> dialog?.dismiss() }.show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        loading.dismiss()
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }

    protected fun hideActionBar() {
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    protected fun setTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.run {
            this.title = title
            show()
        }
    }

    protected inline infix fun <T> LiveData<T>.bindTo(crossinline action: (T) -> Unit) =
        this.observe(viewLifecycleOwner) { action(it) }

}