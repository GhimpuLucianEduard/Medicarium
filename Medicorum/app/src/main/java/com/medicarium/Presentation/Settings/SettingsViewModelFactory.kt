package com.medicarium.Presentation.Settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.LocalDb.UserRepository

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(
    private val application: Application,
    private val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    var instance: SettingsViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SettingsViewModel(application, userRepository)
        }
        return instance as T
    }
}