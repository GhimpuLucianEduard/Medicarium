package com.medicorum.Presentation.Signup

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.medicorum.Data.ApiServices.AuthService
import com.medicorum.Data.DataModels.UserDataModel
import com.medicorum.Data.Models.User
import com.medicorum.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicorum.Presentation.DataViewModels.UserObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import isEmailValid
import isPasswordValid
import isPhoneNumberValid
import java.util.*
import kotlin.concurrent.fixedRateTimer

class FirstSignupViewModel(private val authService: AuthService) : ViewModel() {


    fun signUp() {
        authService.signUp(UserDataModel())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                Log.e("API_REQ", "Delete request failed: ${it.message}")
            }, {
                Log.i("API_REQ", "Delete request succeeded")
            })
    }

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
        user.value = UserObservable(password = "123123123", confirmPassword = "123123123", email = "dlaffsd@gmail.com", lastName = "f", firstName = "df", phoneNumber = "09099099")
    }

}
