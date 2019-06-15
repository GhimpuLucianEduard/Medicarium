package com.medicarium.Data.LocalDb

import androidx.lifecycle.LiveData
import com.medicarium.Data.Models.User

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override fun getUserData(): User {
        return userDao.getUser()
    }

    override fun updateUser(user: User) {
        userDao.updateUserData(user)
    }

    override fun deleteUserData() {
        userDao.deleteUserData()
    }

    override fun addUserData(user: User) {
        userDao.addUser(user)
    }
}