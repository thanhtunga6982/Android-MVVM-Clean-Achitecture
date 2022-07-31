package com.base.android_mvvm_clean_architecture.ui.login

import android.content.Context
import com.base.android_mvvm_clean_architecture.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.base.android_mvvm_clean_architecture.common.base.BaseViewModel
import kr.enjoyworks.room.common.utils.SingleLiveEvent
import kr.enjoyworks.room.data.local.AppDatabase
import kr.enjoyworks.room.data.local.sharedpfers.AppPrefs
import com.base.android_mvvm_clean_architecture.extension.isEmail
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private var appPrefs: AppPrefs,
    private val appDatabase: AppDatabase
) : BaseViewModel() {

    val emailError = SingleLiveEvent<String>()
    val passwordError = SingleLiveEvent<String>()
    val validLoginSuccessEvent by lazy { SingleLiveEvent<Boolean>() }

    fun validationLogin(email: String, password: String) {
        val validEmail = emailErrorMessage(email).isEmpty()
        val validPassword = passwordErrorMessage(password).isEmpty()
        emailError.value = emailErrorMessage(email)
        passwordError.value = passwordErrorMessage(password)
        if (validEmail && validPassword) {
            validLoginSuccessEvent.value = validLoginSuccess(email, password)
        }
    }

    private fun validLoginSuccess(email: String, password: String): Boolean {
        return getDbUser()?.any { it.email == email } ?: false && getDbUser()?.any { it.password == password } ?: false
    }

    private fun getDbUser() = appPrefs.userInfos

    private fun emailErrorMessage(value: String): String {
        return when {
            value.isEmpty() -> ctx.getString(R.string.msg_please_input_field_email)
            !value.isEmail -> ctx.getString(R.string.msg_please_input_email_correct)
            else -> ""
        }
    }

    private fun passwordErrorMessage(value: String): String {
        return when {
            value.isEmpty() -> ctx.getString(R.string.msg_please_input_field_password)
            value.length < 6 -> ctx.getString(R.string.msg_max_leng_pass_word)
            else -> ""
        }
    }

    fun saveDataUser() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                appPrefs.userInfos = appDatabase.userDao().loadAll()
            }
        }
    }
}