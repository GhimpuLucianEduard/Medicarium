package com.medicarium.Presentation.Settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ChangePinViewModelFactory(
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {
    var instance: ChangePinViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = ChangePinViewModel(application)
        }
        return instance as T
    }
}