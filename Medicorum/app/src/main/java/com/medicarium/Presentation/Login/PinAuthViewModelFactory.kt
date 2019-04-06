package com.medicarium.Presentation.Login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class PinAuthViewModelFactory(
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {
    var instance: PinAuthViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = PinAuthViewModel(application)
        }
        return instance as T
    }
}