package com.medicarium.Presentation.General

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.ApiServices.UserPreferencesService
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Presentation.Services.ToastService

@Suppress("UNCHECKED_CAST")
class GenericInfoViewModelFactory(
    private val application: Application,
    private val userRepository: UserRepository,
    private val userPreferencesService: UserPreferencesService,
    private val toastService: ToastService
): ViewModelProvider.NewInstanceFactory() {
    var instance: GenericInfoViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = GenericInfoViewModel(application, userRepository, userPreferencesService, toastService)
        }
        return instance as T
    }
}