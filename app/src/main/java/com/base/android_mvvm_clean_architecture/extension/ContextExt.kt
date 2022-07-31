package com.base.android_mvvm_clean_architecture.extension

import android.content.Context
import androidx.annotation.DimenRes

fun Context.getDimen(@DimenRes id: Int) = resources.getDimensionPixelSize(id)
