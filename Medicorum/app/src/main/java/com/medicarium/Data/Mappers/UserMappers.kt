package com.medicarium.Data.Mappers

import com.medicarium.Data.DataModels.UserDataModel
import com.medicarium.Data.DataModels.UserWithTokenDataModel
import com.medicarium.Data.Models.User
import com.medicarium.Data.Models.UserWithToken
import com.medicarium.Presentation.DataViewModels.UserObservable

fun User.toUserDataModel() = UserDataModel(
    id = id,
    email = email,
    password = password,
    confirmPassword = confirmPassword,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    gender = gender,
    birthDate = birthDate,
    bloodType = bloodType,
    height = height,
    weight = weight,
    emergencyContactName = emergencyContactName,
    emergencyContactPhoneNumber = emergencyContactPhoneNumber,
    status = status,
    isFirstNameVisible = isFirstNameVisible,
    isLastNameVisible = isLastNameVisible,
    isBirthDateVisible = isBirthDateVisible,
    isPhoneNumberVisible = isPhoneNumberVisible,
    isBloodTypeVisible = isBloodTypeVisible,
    isHeightVisible = isHeightVisible,
    isWeightVisible = isWeightVisible,
    isGenderVisible = isGenderVisible,
    isEmergencyContactPhoneNumberVisible = isEmergencyContactPhoneNumberVisible,
    isEmergencyContactNameVisible = isEmergencyContactNameVisible
)

fun UserDataModel.toUserDomainModel() = User(
    id = id,
    email = email,
    password = password,
    confirmPassword = confirmPassword,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    gender = gender,
    birthDate = birthDate,
    bloodType = bloodType,
    height = height,
    weight = weight,
    emergencyContactName = emergencyContactName,
    emergencyContactPhoneNumber = emergencyContactPhoneNumber,
    status = status,
    isFirstNameVisible = isFirstNameVisible,
    isLastNameVisible = isLastNameVisible,
    isBirthDateVisible = isBirthDateVisible,
    isPhoneNumberVisible = isPhoneNumberVisible,
    isBloodTypeVisible = isBloodTypeVisible,
    isHeightVisible = isHeightVisible,
    isWeightVisible = isWeightVisible,
    isGenderVisible = isGenderVisible,
    isEmergencyContactPhoneNumberVisible = isEmergencyContactPhoneNumberVisible,
    isEmergencyContactNameVisible = isEmergencyContactNameVisible
)

fun UserObservable.toUserDomainModel() = User(
    email = email,
    password = password,
    confirmPassword = confirmPassword,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    emergencyContactPhoneNumber = emergencyNumber,
    isEmergencyContactNameVisible = isEmergencyContactNameVisible,
    emergencyContactName = emergencyContact,
    isEmergencyContactPhoneNumberVisible = isEmergencyContactPhoneNumberVisible,
    bloodType = bloodType,
    isBloodTypeVisible = isBloodTypeVisible,
    gender = gendre,
    isGenderVisible = isGenderVisible,
    weight = weight,
    isWeightVisible = isWeightVisible,
    height = height,
    isHeightVisible = isHeightVisible,
    birthDate = birthDate,
    isBirthDateVisible = isBirthDateVisible,
    id = id
).also { it.dbId = dbId }

fun User.toUserObservable() = UserObservable(
    email = email,
    password = password,
    confirmPassword = confirmPassword,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    emergencyNumber = emergencyContactPhoneNumber,
    isEmergencyContactNameVisible = isEmergencyContactNameVisible,
    emergencyContact = emergencyContactName,
    isEmergencyContactPhoneNumberVisible = isEmergencyContactPhoneNumberVisible,
    bloodType = bloodType,
    isBloodTypeVisible = isBloodTypeVisible,
    gendre = gender,
    isGenderVisible = isGenderVisible,
    weight = weight,
    isWeightVisible = isWeightVisible,
    height = height,
    isHeightVisible = isHeightVisible,
    birthDate = birthDate,
    isBirthDateVisible = isBirthDateVisible,
    dbId = dbId,
    id = id
)

fun UserWithToken.toUserWithTokenDataModel() = UserWithTokenDataModel(
    user = user.toUserDataModel(),
    token = token
)

fun UserWithTokenDataModel.toUserWithTokenDomainModel() = UserWithToken(
    user = user.toUserDomainModel(),
    token = token
)