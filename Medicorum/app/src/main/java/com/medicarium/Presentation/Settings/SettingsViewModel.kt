package com.medicarium.Presentation.Settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Data.Models.User
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Presentation.Services.ToastService
import com.medicarium.Utilities.Event
import com.medicarium.Utilities.SharedPreferences.Companion.EMERGENCY_MODE_ACTIVE

class SettingsViewModel(
    application: Application,
    private val userRepository: UserRepository,
    private val toastService: ToastService
) : BaseAndroidViewModel(application) {

    val navigateToLogin = MutableLiveData<Event<Boolean>>()
    var user: User = userRepository.getUserData()

    var isEmergencyModeOn = MutableLiveData<Boolean>()


    init {
        isEmergencyModeOn.value = preferences.get(EMERGENCY_MODE_ACTIVE, false)
    }

    fun signout() {
        preferences.erase()
        preferences.apply()
        userRepository.deleteUserData()
        navigateToLogin.value = Event(true)
    }

    fun setPreferences(checked: Boolean) {
        preferences.put(EMERGENCY_MODE_ACTIVE, checked)
        preferences.apply()
    }

}
