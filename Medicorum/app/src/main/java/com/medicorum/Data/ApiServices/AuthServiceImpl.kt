package com.medicorum.Data.ApiServices

import com.medicorum.Data.ApiServices.RefitServices.ApiAuthService
import com.medicorum.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicorum.Data.DataModels.SmsCheckDataModel
import com.medicorum.Data.Mappers.toUserDataModel
import com.medicorum.Data.Mappers.toUserDomainModel
import com.medicorum.Data.Mappers.toUserWithTokenDomainModel
import com.medicorum.Data.Models.User
import com.medicorum.Data.Models.UserWithToken
import io.reactivex.Observable

class AuthServiceImpl(serviceFactory: ApiServiceFactory) : AuthService{

    private var apiAuthService: ApiAuthService = serviceFactory.create(ApiAuthService::class.java)

    override fun signUp(user: User): Observable<User> {
        return apiAuthService.signUp(user.toUserDataModel())
            .map { item -> item.toUserDomainModel() }
    }

    override fun smsCheck(code: String, userId: String): Observable<UserWithToken> {
        return apiAuthService.smsCheck(SmsCheckDataModel(code, userId))
            .map { item -> item.toUserWithTokenDomainModel() }
    }

}
