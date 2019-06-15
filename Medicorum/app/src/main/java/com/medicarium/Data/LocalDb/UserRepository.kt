package com.medicarium.Data.LocalDb

import androidx.lifecycle.LiveData
import com.medicarium.Data.Models.User

interface UserRepository {
    fun getUserData(): User
    fun updateUser(user: User)
    fun deleteUserData()
    fun addUserData(user: User)
}