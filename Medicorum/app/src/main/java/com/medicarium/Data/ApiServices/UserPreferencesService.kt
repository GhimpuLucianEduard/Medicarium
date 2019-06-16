package com.medicarium.Data.ApiServices

import com.medicarium.Data.Models.User
import io.reactivex.Observable

interface UserPreferencesService {
    fun updateUserPreferences(user: User) : Observable<User>
}