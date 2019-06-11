package com.medicarium.Data.LocalDb

import androidx.lifecycle.LiveData
import com.medicarium.Data.Models.User

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    val data: LiveData<User> = userDao.getUser()

    override fun getUserData(): LiveData<User> {
        return data
    }

    override fun updateUser(user: User) {
        userDao.updateUserData(user)
    }

    override fun deleteUserData() {
        userDao.deleteUserData()
    }

    override fun addUserData(user: User) {
        deleteUserData()
        userDao.addUser(user)
    }
}