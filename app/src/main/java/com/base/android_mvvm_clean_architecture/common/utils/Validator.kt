package kr.enjoyworks.room.common.utils

import android.util.Patterns

object Validator {

    private const val MIN_PASSWORD_LENGTH = 5

    fun validateEmail(email: String?): Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePasswordLength(password: String?): Boolean {
        return !password.isNullOrEmpty() && password.length > MIN_PASSWORD_LENGTH
    }
}