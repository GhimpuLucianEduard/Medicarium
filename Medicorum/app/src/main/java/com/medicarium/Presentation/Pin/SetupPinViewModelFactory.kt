package com.medicarium.Presentation.Pin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Presentation.Services.DialogService

@Suppress("UNCHECKED_CAST")
class SetupPinViewModelFactory(
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {

    var instance: SetupPinViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SetupPinViewModel(application)
        }
        return instance as T
    }
}