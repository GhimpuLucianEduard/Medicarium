package com.medicorum.Data.ApiServices.RefitServices

import com.medicorum.Data.DataModels.SmsCheckDataModel
import com.medicorum.Data.DataModels.UserDataModel
import com.medicorum.Data.DataModels.UserWithTokenDataModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiAuthService {

    @POST("/auth/signup")
    fun signUp(@Body userDataModel: UserDataModel) : Observable<UserDataModel>

    @POST("/auth/check2fa")
    fun smsCheck(@Body smsCheckDataModel: SmsCheckDataModel) : Observable<UserWithTokenDataModel>
}