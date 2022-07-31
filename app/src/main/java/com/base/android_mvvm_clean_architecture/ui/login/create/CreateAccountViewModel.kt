package com.base.android_mvvm_clean_architecture.ui.login.create

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
import com.base.android_mvvm_clean_architecture.model.UserInfo
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val appDatabase: AppDatabase,
    private val appPrefs: AppPrefs
) : BaseViewModel() {

    init { saveDataUser() }

    val createErrorEvent = SingleLiveEvent<String>()
    val createEvent by lazy { SingleLiveEvent<Boolean>() }
    fun validationCreateAccount(userInfo: UserInfo) {

        val errorMail = emailErrorMessage(userInfo.email).isNotEmpty()
        val errorPassword = passwordErrorMessage(userInfo.password).isNotEmpty()
        when {
            errorMail -> {
                createErrorEvent.value = emailErrorMessage(userInfo.email)
                return
            }
            errorPassword -> {
                createErrorEvent.value = passwordErrorMessage(userInfo.password)
                return
            }
            userInfo.name?.isEmpty() == true -> {
                createErrorEvent.value = "Please enter user name "
                return
            }
            !errorMail && !errorPassword -> {
                launchDataLoad {
                    withContext(Dispatchers.IO) {
                        appDatabase.userDao().insert(userInfo)
                        createEvent.postValue(true)
                    }
                }
            }
        }

    }

    private fun emailErrorMessage(value: String?): String {
        val emailExist = appPrefs.userInfos?.any { it.email == value }
        return when {
            value?.isEmpty() == true -> ctx.getString(R.string.msg_please_input_field_email)
            value?.isEmail == false -> ctx.getString(R.string.msg_please_input_email_correct)
            emailExist == true -> ctx.getString(R.string.msg_please_input_email_exist)
            else -> ""
        }
    }

    private fun passwordErrorMessage(value: String?): String {
        return when {
            value?.isEmpty() == true -> ctx.getString(R.string.msg_please_input_field_password)
            value?.length!! < 6 -> ctx.getString(R.string.msg_max_leng_pass_word)
            else -> ""
        }
    }

    private fun saveDataUser() {
        launchDataLoad {
            withContext(Dispatchers.IO) {
                appPrefs.userInfos = appDatabase.userDao().loadAll()
            }
        }
    }
}
