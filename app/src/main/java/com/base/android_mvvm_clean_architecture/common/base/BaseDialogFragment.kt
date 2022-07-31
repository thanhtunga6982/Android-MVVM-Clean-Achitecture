package kr.enjoyworks.room.common.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.base.android_mvvm_clean_architecture.R
import com.base.android_mvvm_clean_architecture.common.base.BaseActivity
import com.base.android_mvvm_clean_architecture.extension.getDimen
import com.base.android_mvvm_clean_architecture.ui.dialog.DialogListener

abstract class BaseDialogFragment<T : BaseDialogFragment<T, VB>, VB : ViewBinding> : DialogFragment() {

    private var _binding: VB? = null
    protected val bind get() = _binding!!
    protected var dialogListener: DialogListener? = null
    protected val baseActivity by lazy { requireActivity() as BaseActivity<*, *> }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateViewBinding(inflater, container)
        return bind.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.run {
            requestFeature(Window.FEATURE_NO_TITLE)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            dialog?.window?.run {
                val params = this.attributes
                params.width = widthDialog()
                params.height = if (heightDialog() > 0) heightDialog() else LinearLayout.LayoutParams.WRAP_CONTENT
                attributes = params
            }
        }
        initializeAction()
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun initializeView(view: View, savedInstanceState: Bundle?)

    open fun initializeAction() {
    }

    protected open fun widthDialog(): Int {
        return baseActivity.getDimen(R.dimen.dialog_width)
    }

    protected open fun heightDialog(): Int {
        return 0
    }

    fun show(manager: FragmentManager) {
        super.show(manager, null)
    }

    @Suppress("UNCHECKED_CAST")
    fun setDialogListener(dialogListener: DialogListener?): T {
        this.dialogListener = dialogListener
        return this as T
    }

    override fun onDestroyView() {
        _binding = null
        dialogListener?.let { dialogListener = null }
        super.onDestroyView()
    }
}
