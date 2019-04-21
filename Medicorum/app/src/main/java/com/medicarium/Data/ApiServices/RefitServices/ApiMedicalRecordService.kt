package com.medicarium.Data.ApiServices.RefitServices

import com.medicarium.Data.DataModels.MedicalRecordDataModel
import io.reactivex.Observable
import retrofit2.http.*

interface ApiMedicalRecordService {

    @POST("/medicalHistory")
    fun addMedicalRecord(@Body medicalRecord: MedicalRecordDataModel) : Observable<MedicalRecordDataModel>

    @GET("/medicalHistory")
    fun getMedicalRecords() : Observable<List<MedicalRecordDataModel>>

    @DELETE("/medicalHistory/{id}")
    fun deleteMedicalRecord(@Path("id") id: String) : Observable<Any>

    @PATCH("/medicalHistory")
    fun editMedicalRecord(@Body medicalRecord: MedicalRecordDataModel) : Observable<MedicalRecordDataModel>

}