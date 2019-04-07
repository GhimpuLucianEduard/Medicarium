package com.medicarium.Data.ApiServices

import com.medicarium.Data.Models.User
import com.medicarium.Data.Models.UserWithToken
import io.reactivex.Observable

interface AuthService {
    fun login(email: String, password: String) : Observable<UserWithToken>
    fun signUp(user: User) : Observable<User>
    fun smsCheck(code: String, userId: String) : Observable<UserWithToken>
}