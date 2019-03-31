package com.medicorum.Data.Mappers

import com.medicorum.Data.DataModels.UserDataModel
import com.medicorum.Data.Models.User
import com.medicorum.Presentation.DataViewModels.UserObservable

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
    healthIssues = healthIssues,
    onGoingTreatments = onGoingTreatments,
    allergies = allergies,
    emergencyContactName = emergencyContactName,
    emergencyContactPhoneNumber = emergencyContactPhoneNumber
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
    healthIssues = healthIssues,
    onGoingTreatments = onGoingTreatments,
    allergies = allergies,
    emergencyContactName = emergencyContactName,
    emergencyContactPhoneNumber = emergencyContactPhoneNumber
)

fun UserObservable.toUserDomainModel() = User(
    email = email,
    password = password,
    confirmPassword = confirmPassword,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber
)