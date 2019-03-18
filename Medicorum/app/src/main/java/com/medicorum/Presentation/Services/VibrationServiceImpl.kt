package com.medicorum.Presentation.Services

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationServiceImpl : VibrationService {

    override fun vibrate(context: Context, timeInMs: Long) {
        val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(timeInMs, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(timeInMs);
        }
    }
}
