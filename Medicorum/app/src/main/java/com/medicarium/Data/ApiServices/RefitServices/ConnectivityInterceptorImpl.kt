package com.medicarium.Data.ApiServices.RefitServices

import com.medicarium.MedicorumApp
import com.medicarium.Presentation.Services.ConnectivityService
import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptorImpl(private val connectivityService: ConnectivityService) :
    ConnectivityInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityService.hasConnection(MedicorumApp.applicationContext())) {
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }
}