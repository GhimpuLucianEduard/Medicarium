package com.medicarium.Presentation.General

import android.app.Application
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Data.Mappers.toUserDomainModel
import com.medicarium.Data.Mappers.toUserObservable
import com.medicarium.Data.Models.User
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicarium.Presentation.DataViewModels.UserObservable

class GenericInfoViewModel(
    application: Application,
    private val userRepository: UserRepository
) : BaseAndroidViewModel(application) {

    var user = PropertyAwareMutableLiveData<UserObservable>()
    lateinit var userClone: User

    init {
        userClone = userRepository.getUserData()
        user.value = userClone.toUserObservable()
    }

    fun updateUserData() {
        userRepository.updateUser(user.value!!.toUserDomainModel())
    }

    fun resetUser() {
        user.value = userClone.toUserObservable()
    }
}