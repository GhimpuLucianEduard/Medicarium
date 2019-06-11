package com.medicarium.Presentation.General

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.LocalDb.UserRepository

@Suppress("UNCHECKED_CAST")
class GenericInfoViewModelFactory(
    private val application: Application,
    private val userRepository: UserRepository
): ViewModelProvider.NewInstanceFactory() {
    var instance: GenericInfoViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = GenericInfoViewModel(application, userRepository)
        }
        return instance as T
    }
}