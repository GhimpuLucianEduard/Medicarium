package com.medicarium.Presentation.General

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.medicarium.Data.ApiServices.UserPreferencesService
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Data.Mappers.toUserDataModel
import com.medicarium.Data.Mappers.toUserDomainModel
import com.medicarium.Data.Mappers.toUserObservable
import com.medicarium.Data.Models.User
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicarium.Presentation.DataViewModels.UserObservable
import com.medicarium.Presentation.Services.ToastService
import com.medicarium.Utilities.LoggerConstants.Companion.API_REQ
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class GenericInfoViewModel(
    application: Application,
    private val userRepository: UserRepository,
    private val userPreferencesService: UserPreferencesService,
    private val toastService: ToastService
) : BaseAndroidViewModel(application) {

    val isBusy = MutableLiveData<Boolean>()
    var user = PropertyAwareMutableLiveData<UserObservable>()
    lateinit var userClone: User

    init {
        userClone = userRepository.getUserData()
        user.value = userClone.toUserObservable()
    }

    fun updateUserData() {
        isBusy.value = true
        var data = user.value!!.toUserDomainModel().toUserDataModel()
        userPreferencesService.updateUserPreferences(user.value!!.toUserDomainModel())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(API_REQ, "User update request succeeded, got usr: $it")
                userRepository.updateUser(user.value!!.toUserDomainModel())
                toastService.showToast(getApplication(), "User data updated.")
            }, {
                Log.e(API_REQ, "User update request failes: ${it.message}")
                toastService.showToast(getApplication(), "There's been an error.")
                isBusy.value = false
            }, {
                isBusy.value = false
            })
            .addTo(compositeDisposable)
    }

    fun resetUser() {
        user.value = userClone.toUserObservable()
    }
}