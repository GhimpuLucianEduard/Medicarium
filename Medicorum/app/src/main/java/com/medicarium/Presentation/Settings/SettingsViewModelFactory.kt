package com.medicarium.Presentation.Settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Presentation.Services.ToastService

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(
    private val application: Application,
    private val userRepository: UserRepository,
    private val toastService: ToastService
): ViewModelProvider.NewInstanceFactory() {
    var instance: SettingsViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = SettingsViewModel(application, userRepository, toastService)
        }
        return instance as T
    }
}