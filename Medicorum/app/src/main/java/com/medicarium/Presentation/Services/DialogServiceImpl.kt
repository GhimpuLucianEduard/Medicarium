package com.medicarium.Presentation.Services

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class DialogServiceImpl : DialogService {

    override fun showNeutralDialog(
        context: Context,
        title: String,
        message: String,
        buttonText: String,
        clickListener: DialogInterface.OnClickListener
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(buttonText, clickListener)
            .create()

        dialog.show()
    }

    override fun showNeutralDialogWithIcon(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        buttonText: String,
        clickListener: DialogInterface.OnClickListener
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setIcon(icon)
            .setNeutralButton(buttonText, clickListener)
            .create()

        dialog.show()
    }
}