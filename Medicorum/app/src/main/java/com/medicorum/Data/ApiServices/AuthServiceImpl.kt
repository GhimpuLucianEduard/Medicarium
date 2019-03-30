package com.medicorum.Data.ApiServices

import com.medicorum.Data.ApiServices.RefitServices.ApiAuthService
import com.medicorum.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicorum.Data.DataModels.UserDataModel
import io.reactivex.Observable

class AuthServiceImpl(serviceFactory: ApiServiceFactory) : AuthService{

    var apiAuthService: ApiAuthService = serviceFactory.create(ApiAuthService::class.java)

    override fun signUp(userDataModel: UserDataModel): Observable<Any> {
        return apiAuthService.signUp(userDataModel)
    }
}
