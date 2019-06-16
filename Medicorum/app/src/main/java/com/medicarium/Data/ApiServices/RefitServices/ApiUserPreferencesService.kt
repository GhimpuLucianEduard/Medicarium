package com.medicarium.Data.ApiServices.RefitServices

import com.medicarium.Data.DataModels.UserDataModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ApiUserPreferencesService {

    @PATCH("/userPreferences")
    fun editMedicalRecord(@Body user: UserDataModel) : Observable<UserDataModel>
}