package com.medicarium.Presentation.General

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class GenericInfoViewModelFactory(
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {
    var instance: GenericInfoViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = GenericInfoViewModel(application)
        }
        return instance as T
    }
}