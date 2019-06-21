package com.medicarium.Presentation.Settings

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Utilities.SharedPreferences
import empty

class ChangePinViewModel (
    application: Application
) : BaseAndroidViewModel(application) {

    val oldPin = MutableLiveData<String>()
    val pinCode = MutableLiveData<String>()
    val confirmPinCode = MutableLiveData<String>()

    init {
        oldPin.value = String.empty()
        pinCode.value = String.empty()
        confirmPinCode.value = String.empty()
    }

    fun onSaveClicked() {
        preferences.put(SharedPreferences.PIN, pinCode.value!!)
        preferences.apply()
    }
}
