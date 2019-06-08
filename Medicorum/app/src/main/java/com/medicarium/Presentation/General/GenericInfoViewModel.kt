package com.medicarium.Presentation.General

import android.app.Application
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicarium.Presentation.DataViewModels.UserObservable

class GenericInfoViewModel(
    application: Application
) : BaseAndroidViewModel(application) {

    var user = PropertyAwareMutableLiveData<UserObservable>()

    init {
        user.value = UserObservable()
    }
}