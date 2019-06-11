package com.medicarium.Presentation.General

import android.app.Application
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicarium.Presentation.DataViewModels.UserObservable

class GenericInfoViewModel(
    application: Application,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application) {

    var user = PropertyAwareMutableLiveData<UserObservable>()

    init {
        user.value = UserObservable()
    }
}