package com.medicorum.Presentation.Services

import android.content.Context

interface VibrationService {
    fun vibrate(context: Context, timeInMs: Long)
}