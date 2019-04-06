package com.medicarium.Presentation.Login

import android.app.Application
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Utilities.SharedPreferences
import empty

class PinAuthViewModel(
    application: Application
) : BaseAndroidViewModel(application) {

    var pin: String = String.empty()

    fun updatePin(text: CharSequence?) {
        pin += text.toString()
    }

    fun isPinValid(): Boolean {
        return pin == preferences.get(SharedPreferences.PIN, "1234")
    }
}