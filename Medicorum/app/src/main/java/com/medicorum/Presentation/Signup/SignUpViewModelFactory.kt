package com.medicorum.Presentation.Signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    var instance: SignUpViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SignUpViewModel()
        }
        return instance as T
    }
}
