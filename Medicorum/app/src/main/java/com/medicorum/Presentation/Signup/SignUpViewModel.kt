package com.medicorum.Presentation.Signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.medicorum.Data.ApiServices.AuthService
import com.medicorum.Data.Mappers.toUserDomainModel
import com.medicorum.Presentation.BaseAndroidViewModel
import com.medicorum.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicorum.Presentation.DataViewModels.UserObservable
import com.medicorum.Presentation.Services.ToastService
import com.medicorum.Utilities.Event
import com.medicorum.Utilities.LiveDataDoubleTrigger
import com.medicorum.Utilities.LoggerConstants.Companion.API_REQ
import com.medicorum.Utilities.SharedPreferences.Companion.FILE_NAME
import com.medicorum.Utilities.SharedPreferences.Companion.SECRET_KEY
import com.medicorum.Utilities.SharedPreferences.Companion.USER_VERIFIED
import empty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import isEmailValid
import isPasswordValid
import isPhoneNumberValid

class SignUpViewModel(
    application: Application,
    private val authService: AuthService,
    private val toastService: ToastService
) : BaseAndroidViewModel(application) {

    private val preferences = CryptoPrefs(getApplication(), FILE_NAME, SECRET_KEY)

    val isBusy = MutableLiveData<Boolean>()
    val user = PropertyAwareMutableLiveData<UserObservable>()
    val smsCode = MutableLiveData<String>()

    val navigateToSmsVerification = MutableLiveData<Event<Boolean>>()

    val isContinueEnabled: LiveData<Boolean> =
        Transformations.map(LiveDataDoubleTrigger(user, isBusy)) {
            it.first!!.email.isEmailValid()
                    && it.first!!.firstName.isNotEmpty()
                    && it.first!!.lastName.isNotEmpty()
                    && it.first!!.phoneNumber.isPhoneNumberValid()
                    && it.first!!.password.isPasswordValid()
                    && it.first!!.password == it.first!!.confirmPassword
                    && it.second!!.not()
        }

    init {
        user.value = UserObservable(
            password = "123123123",
            confirmPassword = "123123123",
            email = "dlaffsd@gmail.com",
            lastName = "f",
            firstName = "df",
            phoneNumber = "0728189341"
        )

        isBusy.value = false
        smsCode.value = String.empty()
    }

    fun signUp() {

        isBusy.value = true

        authService.signUp(user.value?.toUserDomainModel()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toastService.showToast(getApplication(), "success")
                Log.i(API_REQ, "SignUp request succeeded, got usr: $it")
                preferences.put(USER_VERIFIED, false)
                isBusy.value = false
                navigateToSmsVerification.value = Event(true)
            }, {
                toastService.showToast(getApplication(), "fail")
                Log.e(API_REQ, "Auth request failed: ${it.message}")
                isBusy.value = false
            }, {})
            .addTo(compositeDisposable)
    }

    fun smsCheck() {
        isBusy.value = true
        Log.i("API_REQ", smsCode.value.toString())
    }

}