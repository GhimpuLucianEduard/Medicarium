package com.medicorum.Presentation.Services

import android.content.Context

interface ConnectivityService {
    fun hasConnection(context: Context): Boolean
}