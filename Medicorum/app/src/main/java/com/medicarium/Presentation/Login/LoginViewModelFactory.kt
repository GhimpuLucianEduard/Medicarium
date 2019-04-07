package com.medicarium.Presentation.Login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.ApiServices.AuthService

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val application: Application,
    private val authService: AuthService
): ViewModelProvider.NewInstanceFactory() {
    var instance: LoginViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = LoginViewModel(application, authService)
        }
        return instance as T
    }
}