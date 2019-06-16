package com.medicarium.Data.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.medicarium.Data.Enums.BloodType
import com.medicarium.Data.Enums.Gender
import com.medicarium.Utilities.DateTimeUtility
import empty

@Entity(tableName = "user")
data class User(
    var id: String = String.empty(),
    var email: String = String.empty(),
    var password: String = String.empty(),
    var confirmPassword: String = String.empty(),
    var firstName: String = String.empty(),
    var isFirstNameVisible: Boolean = true,
    var lastName: String = String.empty(),
    var isLastNameVisible: Boolean = true,
    var phoneNumber: String = String.empty(),
    var isPhoneNumberVisible: Boolean = true,
    var gender: Gender = Gender.UNSPECIFIED,
    var isGenderVisible: Boolean = true,
    var birthDate: Long = DateTimeUtility.getCurrentDateInMs(),
    var isBirthDateVisible: Boolean = true,
    var bloodType: BloodType = BloodType.UNSPECIFIED,
    var isBloodTypeVisible: Boolean = true,
    var height: String = String.empty(),
    var isHeightVisible: Boolean = true,
    var weight: String = String.empty(),
    var isWeightVisible: Boolean = true,
    var emergencyContactName: String = String.empty(),
    var emergencyContactPhoneNumber: String = String.empty(),
    var isEmergencyContactNameVisible: Boolean = true,
    var isEmergencyContactPhoneNumberVisible: Boolean = true,
    var status: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var dbId: Int? = null
}
