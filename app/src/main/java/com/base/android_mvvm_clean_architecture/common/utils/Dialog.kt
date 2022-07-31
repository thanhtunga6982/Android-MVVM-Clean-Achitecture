package kr.enjoyworks.room.common.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object Dialog {

    fun createOneButtonDialog(
        context: Context,
        title: String?,
        message: String?,
        positiveButton: String?,
        positiveOnClickListener: DialogInterface.OnClickListener?,
    ): AlertDialog {
        return AlertDialog.Builder(context).apply {
            setPositiveButton(positiveButton, positiveOnClickListener)
            setTitle(title)
            setMessage(message)
        }.create()
    }
}