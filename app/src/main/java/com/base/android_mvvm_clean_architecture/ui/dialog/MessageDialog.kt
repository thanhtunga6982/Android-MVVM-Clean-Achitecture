package com.base.android_mvvm_clean_architecture.ui.dialog

import com.base.android_mvvm_clean_architecture.databinding.DialogMessageBinding
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.base.android_mvvm_clean_architecture.extension.visible
import com.base.android_mvvm_clean_architecture.common.base.BaseActivity
import kr.enjoyworks.room.common.base.BaseDialogFragment
import com.base.android_mvvm_clean_architecture.common.base.BaseFragment
import com.base.android_mvvm_clean_architecture.extension.argument

class MessageDialog : BaseDialogFragment<MessageDialog, DialogMessageBinding>() {

    private var title by argument<String>()
    private var message by argument<String>()
    private var negativeLabel by argument<String>()
    private var positiveLabel by argument<String>()
    private var isAllowDismiss by argument<Boolean>()

    private var countDownTimer: CountDownTimer? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogMessageBinding {
        return DialogMessageBinding.inflate(inflater, container, false)
    }

    override fun initializeView(view: View, savedInstanceState: Bundle?) = with(bind) {
        if (negativeLabel.isNotBlank()) {
            btnNegative.visible()
            btnNegative.text = negativeLabel.trim()
            viewSplit.visible()
        }

        if (positiveLabel.isNotBlank()) {
            btnPositive.text = positiveLabel.trim()
        }

        if (title.isNotBlank()) {
            tvTitle.text = title
            tvTitle.visible()
        }

        if (message.isNotBlank()) {
            tvMessage.text = message
            tvMessage.visible()
        }

        btnNegative.setOnClickListener {
            dialogListener?.run { onNegative() }
            if (isVisible && !isRemoving) dismiss()
        }
        btnPositive.setOnClickListener {
            dialogListener?.run { onPositive() }
            if (isAllowDismiss && isVisible && !isRemoving) dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        dialogListener?.run { onDismiss() }
        countDownTimer?.cancel()
        super.onDismiss(dialog)
    }

    companion object {
        fun newInstance(
            title: String = "",
            message: String,
            negativeLabel: String = "",
            positiveLabel: String = "",
            isAllowDismiss: Boolean = true,
        ): MessageDialog {
            return MessageDialog().apply {
                this.title = title
                this.message = message
                this.negativeLabel = negativeLabel
                this.positiveLabel = positiveLabel
                this.isAllowDismiss = isAllowDismiss
            }
        }
    }
}

/**
 * Show single confirm dialog
 */
private var showMsgDialog: MessageDialog? = null

fun BaseActivity<*, *>?.showMessageDialog(
    title: String = "",
    message: String,
    negativeLabel: String = "",
    positiveLabel: String = "",
    cancelable: Boolean = true,
    dialogListener: DialogListener? = null,
) {
    this?.safeFragmentTransaction?.registerFragmentTransition { fm ->
        MessageDialog.newInstance(title, message, negativeLabel, positiveLabel).apply {
            isCancelable = cancelable
            setDialogListener(dialogListener)
            if (showMsgDialog?.isVisible == true && showMsgDialog?.isRemoving == false) {
                showMsgDialog?.dismiss()
            }
            showMsgDialog = this
            show(fm)
        }
    }
}

fun BaseFragment<*, *>?.showMessageDialog(
    title: String = "",
    message: String,
    negativeLabel: String = "",
    positiveLabel: String = "",
    cancelable: Boolean = true,
    isAllowDismiss: Boolean = true,
    dialogListener: DialogListener? = null,
) {
    this?.safeFragmentTransaction?.registerFragmentTransition { fm ->
        MessageDialog.newInstance(title, message, negativeLabel, positiveLabel, isAllowDismiss)
            .apply {
                isCancelable = cancelable
                setDialogListener(dialogListener)
                if (showMsgDialog?.isVisible == true && showMsgDialog?.isRemoving == false) {
                    showMsgDialog?.dismiss()
                }
                showMsgDialog = this
                show(fm)
            }
    }
}
