package com.medicarium.Data.DataModels

import com.google.gson.annotations.SerializedName
import com.medicarium.Data.Enums.BloodType
import com.medicarium.Data.Enums.Gender
import com.medicarium.Utilities.DateTimeUtility
import empty

data class UserDataModel(
    @SerializedName("_id")
    var id: String = String.empty(),
    var email: String = String.empty(),
    var password: String = String.empty(),
    var confirmPassword: String = String.empty(),
    var firstName: String = String.empty(),
    var lastName: String = String.empty(),
    var phoneNumber: String = String.empty(),
    var gender: Gender = Gender.UNSPECIFIED,
    var birthDate: Long = DateTimeUtility.getCurrentDateInMs(),
    var bloodType: BloodType = BloodType.UNSPECIFIED,
    var height: String = String.empty(),
    var weight: String = String.empty(),
    var healthIssues: List<String> = ArrayList(),
    var onGoingTreatments: List<String> = ArrayList(),
    var allergies: List<String> = ArrayList(),
    var emergencyContactName: String = String.empty(),
    var emergencyContactPhoneNumber: String = String.empty(),
    var status: Boolean = false,
    var isFirstNameVisible: Boolean = false,
    var isLastNameVisible: Boolean = false,
    var isPhoneNumberVisible: Boolean = false,
    var isGenderVisible: Boolean = false,
    var isBirthDateVisible: Boolean = false,
    var isBloodTypeVisible: Boolean = false,
    var isHeightVisible: Boolean = true,
    var isWeightVisible: Boolean = false,
    var isEmergencyContactNameVisible: Boolean = false,
    var isEmergencyContactPhoneNumberVisible: Boolean = false
)
