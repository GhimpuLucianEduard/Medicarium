package com.medicarium.Presentation.Services

import android.content.Context
import android.content.DialogInterface

interface DialogService {
    fun showNeutralDialog(
        context: Context,
        title: String,
        message: String,
        buttonText: String,
        clickListener: DialogInterface.OnClickListener)

    fun showNeutralDialogWithIcon(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        buttonText: String,
        clickListener: DialogInterface.OnClickListener)
}