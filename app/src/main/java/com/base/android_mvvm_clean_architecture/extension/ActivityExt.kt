package com.base.android_mvvm_clean_architecture.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.base.android_mvvm_clean_architecture.R

fun Activity.hideSoftKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Window.setTransparentStatusBar(isLightStatusBar: Boolean = true) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        WindowCompat.setDecorFitsSystemWindows(this, false)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(this, decorView)
            .isAppearanceLightStatusBars = isLightStatusBar
    }
}


inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

fun AppCompatActivity.finishSlideOut() {
    finish()
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    bundle: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent)
}