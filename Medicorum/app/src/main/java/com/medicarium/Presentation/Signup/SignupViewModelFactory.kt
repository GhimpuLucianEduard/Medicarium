package com.medicarium.Presentation.Signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.ApiServices.AuthService
import com.medicarium.Presentation.Services.ToastService

@Suppress("UNCHECKED_CAST")
class SignupViewModelFactory(
    private val application: Application,
    private val authService: AuthService,
    private val toastService: ToastService
): ViewModelProvider.NewInstanceFactory() {

    var instance: SignupViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SignupViewModel(application, authService, toastService)
        }
        return instance as T
    }
}
