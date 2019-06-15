package com.medicarium.Presentation.Login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.medicarium.Data.ApiServices.AuthService
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Utilities.Event
import com.medicarium.Utilities.LiveDataDoubleTrigger
import com.medicarium.Utilities.LoggerConstants
import com.medicarium.Utilities.SharedPreferences
import empty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import isEmailValid
import isPasswordValid
import retrofit2.HttpException

class LoginViewModel(
    application: Application,
    private val authService: AuthService,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application) {

    private val _showDialog = MutableLiveData<String>()

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isBusy = MutableLiveData<Boolean>()

    val showDialog: LiveData<String>
        get() = _showDialog

    val navigateToPinSetup = MutableLiveData<Event<Boolean>>()
    val navigateToSmsCheck = MutableLiveData<Event<Boolean>>()

    val isLoginEnabled: LiveData<Boolean> =
        Transformations.map(LiveDataDoubleTrigger(email, password)) {
            it.first!!.isEmailValid() && it.second!!.isPasswordValid()
        }

    init {
        email.value = String.empty()
        password.value = String.empty()
    }

    fun handleLoginClicked() {

        isBusy.value = true

        authService.login(email.value!!, password.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LoggerConstants.API_REQ, "login request succeeded, got response: $it")
                if (it.user.status) {

                    preferences.put(SharedPreferences.USER_VERIFIED, true)
                    preferences.put(SharedPreferences.TOKEN, it.token)
                    preferences.apply()
                    userRepository.addUserData(it.user)

                    val doi = userRepository.getUserData()

                    navigateToPinSetup.value = Event(true)
                }
            }, {
                Log.e(LoggerConstants.API_REQ, "Auth request failed: ${it.message}")

                var errorMessage = String.empty()

                when (it) {
                    is HttpException -> {
                        when (it.code()) {
                            404 -> errorMessage = "Invalid credentials"
                            401 -> errorMessage = "Invalid credentials"
                            422 -> errorMessage = "Invalid credentials"
                            500 -> errorMessage = "There may be a problem with the server. Please try again later"
                        }
                    }
                }
                isBusy.value = false

                if (errorMessage != String.empty()) {
                    _showDialog.value = errorMessage
                } else {
                    navigateToSmsCheck.value = Event(true)
                }

            }, {
                isBusy.value = false
            })
            .addTo(compositeDisposable)

        //navigateToPinSetup.value = Event(true)
    }
}
