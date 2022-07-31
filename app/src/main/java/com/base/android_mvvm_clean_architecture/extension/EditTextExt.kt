package com.base.android_mvvm_clean_architecture.extension

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

val EditText.getString: String get() = this.text.toString()

val EditText.getStringTrimmed: String get() = this.getString.trim()

fun EditText.setTheText(text: String) {
    this.setText(text, TextView.BufferType.EDITABLE)
}

fun EditText.isNotEmpty(): Boolean {
    return this.getString.isNotEmpty()
}

inline fun EditText.onKeyboardSubmit(crossinline block: EditText.() -> Unit) {
    setOnEditorActionListener { _, actionId, event ->
        return@setOnEditorActionListener if (isKeyboardSubmit(actionId, event)) {
            hideKeyboard()
            block()
            true
        } else {
            false
        }
    }
}

fun isKeyboardSubmit(actionId: Int, event: KeyEvent?): Boolean =
    actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH ||
        (event != null && event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_ENTER)
