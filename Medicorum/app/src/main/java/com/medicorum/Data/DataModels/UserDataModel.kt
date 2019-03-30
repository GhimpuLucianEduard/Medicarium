package com.medicorum.Data.DataModels

import com.medicorum.Data.Enums.BloodType
import com.medicorum.Data.Enums.Gender
import com.medicorum.Utilities.DateTimeUtility
import empty

data class UserDataModel(
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
    var emergencyContactPhoneNumber: String = String.empty()
)
