package com.medicarium.Presentation.Login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Utilities.Event
import com.medicarium.Utilities.LiveDataDoubleTrigger
import com.medicarium.Utilities.LoggerConstants.Companion.INFO_TAG
import empty
import isEmailValid
import isPasswordValid

class LoginViewModel(
    application: Application
) : BaseAndroidViewModel(application) {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val navigateToPinSetup = MutableLiveData<Event<Boolean>>()

    val isLoginEnabled: LiveData<Boolean> =
        Transformations.map(LiveDataDoubleTrigger(email, password)) {
            it.first!!.isEmailValid() && it.second!!.isPasswordValid()
        }

    init {
        email.value = String.empty()
        password.value = String.empty()
    }

    fun handleLoginClicked() {
        Log.i(INFO_TAG, "email: ${email.value}")
        Log.i(INFO_TAG, "pass: ${password.value}")
        navigateToPinSetup.value = Event(true)
    }
}
