package com.medicarium.Presentation.Services

import android.content.Context

interface ConnectivityService {
    fun hasConnection(context: Context): Boolean
}