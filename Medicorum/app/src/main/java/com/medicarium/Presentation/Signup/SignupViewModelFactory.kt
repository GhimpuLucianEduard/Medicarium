package com.medicarium.Presentation.Signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.ApiServices.AuthService
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Presentation.Services.ToastService

@Suppress("UNCHECKED_CAST")
class SignupViewModelFactory(
    private val application: Application,
    private val authService: AuthService,
    private val toastService: ToastService,
    private val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {

    var instance: SignupViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SignupViewModel(application, authService, toastService, userRepository)
        }
        return instance as T
    }
}
