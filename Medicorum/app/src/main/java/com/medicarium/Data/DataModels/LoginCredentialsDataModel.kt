package com.medicarium.Data.DataModels

import empty

data class LoginCredentialsDataModel(
    val email: String = String.empty(),
    val password: String = String.empty()
)