package com.medicarium.Data.ApiServices

import com.medicarium.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicarium.Data.ApiServices.RefitServices.ApiUserPreferencesService
import com.medicarium.Data.Mappers.toUserDataModel
import com.medicarium.Data.Mappers.toUserDomainModel
import com.medicarium.Data.Models.User
import io.reactivex.Observable

class UserPreferencesServiceImpl (serviceFactory: ApiServiceFactory) : UserPreferencesService {

    private var apiService: ApiUserPreferencesService = serviceFactory.create(
        ApiUserPreferencesService::class.java
    )

    override fun updateUserPreferences(user: User): Observable<User> {
        return apiService.editMedicalRecord(user.toUserDataModel())
            .map { it.toUserDomainModel() }
    }
}