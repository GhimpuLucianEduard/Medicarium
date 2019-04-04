package com.medicarium.Presentation.DataViewModels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.medicarium.BR
import empty

class UserObservable(
    email: String = String.empty(),
    password: String = String.empty(),
    confirmPassword: String = String.empty(),
    firstName: String = String.empty(),
    lastName: String = String.empty(),
    phoneNumber: String = String.empty()
): BaseObservable() {

    @Bindable
    var email: String = email
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    @Bindable
    var password: String = password
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    @Bindable
    var confirmPassword: String = confirmPassword
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    @Bindable
    var phoneNumber: String = phoneNumber
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)
        }

    @Bindable
    var firstName: String = firstName
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @Bindable
    var lastName: String = lastName
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }
}