package com.medicarium.Presentation.Services

import android.content.Context
import android.widget.Toast

class ToastServiceImpl : ToastService {
    override fun showToast(context: Context, message: String, length: Int) {
        Toast.makeText(context, message, length).show()
    }
}