package com.medicarium.Presentation.Services

import android.content.Context

interface ToastService {
    fun showToast(context: Context, message: String, length: Int = 0)
}