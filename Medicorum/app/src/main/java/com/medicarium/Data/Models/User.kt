package com.medicarium.Data.Models

import com.medicarium.Data.Enums.BloodType
import com.medicarium.Data.Enums.Gender
import com.medicarium.Utilities.DateTimeUtility
import empty

data class User(
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
