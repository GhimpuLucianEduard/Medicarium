package com.medicorum.Data.ApiServices.RefitServices

import com.medicorum.Data.DataModels.UserDataModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiAuthService {

    @POST("/auth/signup")
    fun signUp(@Body userDataModel: UserDataModel) : Observable<Any>
}