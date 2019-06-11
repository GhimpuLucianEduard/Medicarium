package com.medicarium.Data.LocalDb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.medicarium.Data.Models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT(1)")
    fun getUser() : LiveData<User>

    @Update
    fun updateUserData(user: User)

    @Query("DELETE FROM user")
    fun deleteUserData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)
}