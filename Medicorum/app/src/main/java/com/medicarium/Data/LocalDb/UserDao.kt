package com.medicarium.Data.LocalDb

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.medicarium.Data.Models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = (:givenId)")
    fun getUser(givenId: String) : User

    @Update
    fun updateUserData(user: User)
}