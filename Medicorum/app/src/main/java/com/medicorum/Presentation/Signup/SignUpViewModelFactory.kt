package com.medicorum.Presentation.Signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicorum.Data.ApiServices.AuthService

@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory(val authService: AuthService): ViewModelProvider.NewInstanceFactory() {
    var instance: SignUpViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SignUpViewModel(authService)
        }
        return instance as T
    }
}
