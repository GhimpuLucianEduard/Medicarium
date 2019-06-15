package com.medicarium.Presentation.Login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.ApiServices.AuthService
import com.medicarium.Data.LocalDb.UserRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    private val application: Application,
    private val authService: AuthService,
    private val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    var instance: LoginViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = LoginViewModel(application, authService, userRepository)
        }
        return instance as T
    }
}