package com.medicorum.Data.DataModels

data class UserWithTokenDataModel(
    val user: UserDataModel,
    val token: String
)