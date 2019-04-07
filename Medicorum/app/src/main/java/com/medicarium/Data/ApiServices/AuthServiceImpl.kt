package com.medicarium.Data.ApiServices

import com.medicarium.Data.ApiServices.RefitServices.ApiAuthService
import com.medicarium.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicarium.Data.DataModels.LoginCredentialsDataModel
import com.medicarium.Data.DataModels.SmsCheckDataModel
import com.medicarium.Data.Mappers.toUserDataModel
import com.medicarium.Data.Mappers.toUserDomainModel
import com.medicarium.Data.Mappers.toUserWithTokenDomainModel
import com.medicarium.Data.Models.User
import com.medicarium.Data.Models.UserWithToken
import io.reactivex.Observable

class AuthServiceImpl(serviceFactory: ApiServiceFactory) : AuthService{

    private var apiAuthService: ApiAuthService = serviceFactory.create(ApiAuthService::class.java)

    override fun login(email: String, password: String): Observable<UserWithToken> {
        return apiAuthService.login(LoginCredentialsDataModel(email, password))
            .map { item -> item.toUserWithTokenDomainModel() }
    }

    override fun signUp(user: User): Observable<User> {
        return apiAuthService.signUp(user.toUserDataModel())
            .map { item -> item.toUserDomainModel() }
    }

    override fun smsCheck(code: String, userId: String): Observable<UserWithToken> {
        return apiAuthService.smsCheck(SmsCheckDataModel(code, userId))
            .map { item -> item.toUserWithTokenDomainModel() }
    }

}
