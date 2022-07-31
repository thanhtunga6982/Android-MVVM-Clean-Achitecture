package com.base.android_mvvm_clean_architecture.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.text.Layout
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun viewGones(vararg views: View) {
    for (view in views) {
        view.gone()
    }
}

fun viewVisibles(vararg views: View) {
    for (view in views) {
        view.visible()
    }
}

inline fun View.visibleElseGone(block: () -> Boolean) {
    visibility = if (block()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

inline fun View.goneElseVisible(block: () -> Boolean) {
    visibility = if (block()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

inline fun View.visibleElseInvisible(block: () -> Boolean) {
    visibility = if (block()) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

inline fun View.ifVisible(action: () -> Unit) {
    if (isVisible) action()
}

inline fun View.ifInvisible(action: () -> Unit) {
    if (isInvisible) action()
}

inline fun View.ifGone(action: () -> Unit) {
    if (isGone) action()
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (this.requestFocus()) {
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun View.showKeyboardOnDialog() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (this.requestFocus()) {
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
}

fun View.hideKeyboardOnDialog() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}

fun View.setPaddingTop(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)

fun View.setPaddingBottom(value: Int) = setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)

fun View.setPaddingStart(value: Int) = setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)

fun View.setPaddingEnd(value: Int) = setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)

fun View.setPaddingHorizontal(value: Int) = setPaddingRelative(value, paddingTop, value, paddingBottom)

fun View.setPaddingVertical(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, value)

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

inline fun View.clickCoolDown(
    coolDown: Long = 1000L,
    crossinline action: (view: View) -> Unit
) {
    setOnClickListener(object : View.OnClickListener {
        var lastTime = 0L
        override fun onClick(v: View) {
            val now = System.currentTimeMillis()
            if (now - lastTime > coolDown) {
                action(v)
                lastTime = now
            }
        }
    })
}

inline fun <T> viewSameAction(vararg views: T, block: (T) -> Unit) {
    for (view in views) {
        block(view)
    }
}

fun NestedScrollView.nestedScrollTo(targetView: View) {
    val childOffset = Point()
    getDeepChildOffset(this, targetView.parent, this, childOffset)
    smoothScrollTo(0, childOffset.y)
}

private fun getDeepChildOffset(mainParent: ViewGroup, parent: ViewParent, child: View, accumulatedOffset: Point) {
    val parentGroup = parent as ViewGroup
    accumulatedOffset.x += child.left
    accumulatedOffset.y += child.top
    if (parentGroup == mainParent) {
        return
    }
    getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
}

inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Layout.hasEllipsize(): Boolean = this.getEllipsisCount(this.lineCount - 1) > 0

fun AppCompatTextView.showBadge(count: Int) {
    this.visibleElseGone { count > 0 }
    this.text = if (count > 99) "99+" else "$count"
}
