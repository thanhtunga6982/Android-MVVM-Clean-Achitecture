package com.base.android_mvvm_clean_architecture.ui.dialog

interface DialogListener {
    fun onDismiss() {}

    fun onPositive() {}

    fun onNegative() {}

    fun onDelete() {}
}
