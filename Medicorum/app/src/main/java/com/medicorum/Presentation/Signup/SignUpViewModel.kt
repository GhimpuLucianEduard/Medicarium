package com.medicorum.Presentation.Signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.medicorum.Data.ApiServices.AuthService
import com.medicorum.Data.Mappers.toUserDomainModel
import com.medicorum.Presentation.BaseAndroidViewModel
import com.medicorum.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicorum.Presentation.DataViewModels.UserObservable
import com.medicorum.Presentation.Services.ToastService
import com.medicorum.Utilities.LoggerConstants.Companion.API_REQ
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import isEmailValid
import isPasswordValid
import isPhoneNumberValid
import java.util.concurrent.TimeUnit

class SignUpViewModel(
    application: Application,
    private val authService: AuthService,
    private val toastService: ToastService
) : BaseAndroidViewModel(application) {


    val isBusy = MutableLiveData<Boolean>()
    val user = PropertyAwareMutableLiveData<UserObservable>()

    val isContinueEnabled: LiveData<Boolean> = Transformations.map(user) {
        it.email.isEmailValid()
                && it.firstName.isNotEmpty()
                && it.lastName.isNotEmpty()
                && it.phoneNumber.isPhoneNumberValid()
                && it.password.isPasswordValid()
                && it.password == it.confirmPassword
    }

    init {
        user.value = UserObservable(
            password = "123123123",
            confirmPassword = "123123123",
            email = "dlaffsd@gmail.com",
            lastName = "f",
            firstName = "df",
            phoneNumber = "0728189341")
        isBusy.value = false
    }

    fun signUp() {

        isBusy.value = true

        authService.signUp(user.value?.toUserDomainModel()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toastService.showToast(getApplication(), "success")
                Log.i(API_REQ, "SignUp request succeeded, got usr: $it")
                isBusy.value = false
            }, {
                toastService.showToast(getApplication(), "fail")
                Log.e(API_REQ, "Auth request failed: ${it.message}")
                isBusy.value = false
            }, {})
            .addTo(compositeDisposable)
    }

}