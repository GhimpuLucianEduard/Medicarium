package com.medicarium.Presentation.DataViewModels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.medicarium.BR
import com.medicarium.Data.Enums.BloodType
import com.medicarium.Data.Enums.Gender
import com.medicarium.Utilities.DateTimeUtility
import empty

class UserObservable(
    email: String = String.empty(),
    password: String = String.empty(),
    confirmPassword: String = String.empty(),
    firstName: String = String.empty(),
    lastName: String = String.empty(),
    phoneNumber: String = String.empty(),
    weight: String = String.empty(),
    height: String = String.empty(),
    emergencyContact: String = String.empty(),
    emergencyNumber: String = String.empty(),
    birthDate: Long = DateTimeUtility.getCurrentDateInMs(),
    gendre: Gender = Gender.UNSPECIFIED,
    bloodType: BloodType = BloodType.UNSPECIFIED,
    var id: String = String.empty(),
    var isFirstNameVisible: Boolean = true,
    var isLastNameVisible: Boolean = true,
    var isPhoneNumberVisible: Boolean = true,
    var isGenderVisible: Boolean = true,
    var isBirthDateVisible: Boolean = false,
    var isBloodTypeVisible: Boolean = true,
    var isHeightVisible: Boolean = true,
    var isWeightVisible: Boolean = false,
    var isEmergencyContactNameVisible: Boolean = true,
    var isEmergencyContactPhoneNumberVisible: Boolean = false,
    var dbId: Int? = 0
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

    @Bindable
    var weight: String = weight
        set(value) {
            field = value
            notifyPropertyChanged(BR.weight)
        }

    @Bindable
    var height: String = height
        set(value) {
            field = value
            notifyPropertyChanged(BR.height)
        }

    @Bindable
    var emergencyContact: String = emergencyContact
        set(value) {
            field = value
            notifyPropertyChanged(BR.emergencyContact)
        }

    @Bindable
    var emergencyNumber: String = emergencyNumber
        set(value) {
            field = value
            notifyPropertyChanged(BR.emergencyNumber)
        }

    @Bindable
    var birthDate: Long = birthDate
        set(value) {
            field = value
            notifyPropertyChanged(BR.birthDate)
        }

    @Bindable
    var gendre: Gender = gendre
        set(value) {
            field = value
            notifyPropertyChanged(BR.gendre)
        }

    @Bindable
    var bloodType: BloodType = bloodType
        set(value) {
            field = value
            notifyPropertyChanged(BR.bloodType)
        }
}