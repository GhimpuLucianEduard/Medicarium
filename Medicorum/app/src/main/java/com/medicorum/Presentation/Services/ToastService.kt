package com.medicorum.Presentation.Services

import android.content.Context
import android.widget.Toast

interface ToastService {
    fun showToast(context: Context, message: String, length: Int = 0)
}