package com.medicarium.Presentation.Settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Utilities.Event

class SettingsViewModel(
    application: Application,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application) {

    val navigateToLogin = MutableLiveData<Event<Boolean>>()

    fun signout() {
        preferences.erase()
        preferences.apply()
        userRepository.deleteUserData()
        navigateToLogin.value = Event(true)
    }
}
