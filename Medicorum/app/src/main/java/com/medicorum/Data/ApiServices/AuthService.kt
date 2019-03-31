package com.medicorum.Data.ApiServices

import com.medicorum.Data.DataModels.UserDataModel
import com.medicorum.Data.Models.User
import io.reactivex.Observable

interface AuthService {
    fun signUp(user: User) : Observable<User>
}