package com.medicorum.Data.ApiServices

import com.medicorum.Data.DataModels.UserDataModel
import io.reactivex.Observable

interface AuthService {
    fun signUp(userDataModel: UserDataModel) : Observable<Any>
}