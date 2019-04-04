package com.medicarium.Presentation.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class LoginFragmentViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    var instance: LoginFragmentViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = LoginFragmentViewModel()
        }
        return instance as T
    }
}