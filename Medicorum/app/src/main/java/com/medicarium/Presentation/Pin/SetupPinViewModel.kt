package com.medicarium.Presentation.Pin

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Utilities.LiveDataDoubleTrigger
import com.medicarium.Utilities.SharedPreferences.Companion.PIN
import empty
import isValidPin

class SetupPinViewModel(
    application: Application
) : BaseAndroidViewModel(application) {

    val pinCode = MutableLiveData<String>()
    val confirmPinCode = MutableLiveData<String>()

    val isContinueEnabled: LiveData<Boolean> =
        Transformations.map(LiveDataDoubleTrigger(pinCode, confirmPinCode)) {
            it.first!!.isValidPin() && it.second!!.isValidPin()
        }

    init {
        pinCode.value = String.empty()
        confirmPinCode.value = String.empty()
    }

    fun onPinConfigured() {
        preferences.put(PIN, pinCode.value!!)
        preferences.apply()
    }

}
