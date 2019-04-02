package com.medicorum.Data.ApiServices

import com.medicorum.Data.DataModels.UserDataModel
import com.medicorum.Data.Models.User
import com.medicorum.Data.Models.UserWithToken
import io.reactivex.Observable

interface AuthService {
    fun signUp(user: User) : Observable<User>
    fun smsCheck(code: String, userId: String) : Observable<UserWithToken>
}