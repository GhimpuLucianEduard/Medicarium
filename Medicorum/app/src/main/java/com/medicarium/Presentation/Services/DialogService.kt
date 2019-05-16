package com.medicarium.Presentation.Services

import android.content.Context
import android.content.DialogInterface
import android.view.View

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

    fun showConfirmationAlertWithIcon(
        context: Context,
        title: String,
        message: String,
        icon: Int,
        positiveButtonText: String,
        negativeButtonText: String,
        positiveClickListener: DialogInterface.OnClickListener,
        negativeClickListener: DialogInterface.OnClickListener
    )
}