package com.medicorum.Data.Models

data class UserWithToken(
    val user: User,
    val token: String
)