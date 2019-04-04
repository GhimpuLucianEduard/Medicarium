package com.medicarium.Data.DataModels

data class UserWithTokenDataModel(
    val user: UserDataModel,
    val token: String
)