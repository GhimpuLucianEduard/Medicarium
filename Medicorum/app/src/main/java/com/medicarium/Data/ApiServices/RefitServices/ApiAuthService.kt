package com.medicarium.Data.ApiServices.RefitServices

import com.medicarium.Data.DataModels.LoginCredentialsDataModel
import com.medicarium.Data.DataModels.SmsCheckDataModel
import com.medicarium.Data.DataModels.UserDataModel
import com.medicarium.Data.DataModels.UserWithTokenDataModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiAuthService {

    @POST("/auth/login")
    fun login(@Body loginCredentialsDataModel: LoginCredentialsDataModel) : Observable<UserWithTokenDataModel>

    @POST("/auth/signup")
    fun signUp(@Body userDataModel: UserDataModel) : Observable<UserDataModel>

    @POST("/auth/check2fa")
    fun smsCheck(@Body smsCheckDataModel: SmsCheckDataModel) : Observable<UserWithTokenDataModel>
}