package com.medicarium.Presentation.Services

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityServiceImpl : ConnectivityService {
    override fun hasConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}