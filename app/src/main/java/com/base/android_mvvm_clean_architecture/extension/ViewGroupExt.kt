package com.base.android_mvvm_clean_architecture.extension

import android.view.LayoutInflater
import android.view.ViewGroup

val ViewGroup.inflater: LayoutInflater get() = LayoutInflater.from(context)
