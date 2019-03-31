package com.medicorum.Data.ApiServices

import com.medicorum.Data.ApiServices.RefitServices.ApiAuthService
import com.medicorum.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicorum.Data.Mappers.toUserDataModel
import com.medicorum.Data.Mappers.toUserDomainModel
import com.medicorum.Data.Models.User
import io.reactivex.Observable

class AuthServiceImpl(serviceFactory: ApiServiceFactory) : AuthService{

    var apiAuthService: ApiAuthService = serviceFactory.create(ApiAuthService::class.java)

    override fun signUp(user: User): Observable<User> {
        return apiAuthService.signUp(user.toUserDataModel())
            .map { item -> item.toUserDomainModel() }
    }
}
