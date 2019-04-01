package com.medicorum.Presentation.Signup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicorum.Data.ApiServices.AuthService
import com.medicorum.Presentation.Services.ToastService

@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory(
    private val application: Application,
    private val authService: AuthService,
    private val toastService: ToastService
): ViewModelProvider.NewInstanceFactory() {

    var instance: SignUpViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SignUpViewModel(application, authService, toastService)
        }
        return instance as T
    }
}
