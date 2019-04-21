package com.medicarium.Data.ApiServices

import com.medicarium.Data.ApiServices.RefitServices.ApiMedicalRecordService
import com.medicarium.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicarium.Data.Mappers.toMedicalRecord
import com.medicarium.Data.Mappers.toMedicalRecordDataModel
import com.medicarium.Data.Models.MedicalRecord
import io.reactivex.Observable

class MedicalRecordServiceImpl(serviceFactory: ApiServiceFactory) : MedicalRecordService {

    private var apiMedicalRecordService : ApiMedicalRecordService = serviceFactory.create(
        ApiMedicalRecordService::class.java)

    override fun addMedicalRecord(medicalRecord: MedicalRecord): Observable<MedicalRecord> {
        return apiMedicalRecordService.addMedicalRecord(medicalRecord.toMedicalRecordDataModel())
            .map { it.toMedicalRecord() }
    }

    override fun getMedicalRecord(): Observable<List<MedicalRecord>> {
        return apiMedicalRecordService.getMedicalRecords()
            .flatMapIterable { list -> list }
            .map { it.toMedicalRecord() }
            .toList()
            .toObservable()
    }

    override fun deleteMedicalRecord(id: String): Observable<Any> {
        return apiMedicalRecordService.deleteMedicalRecord(id)
    }

    override fun editMedicalRecord(medicalRecord: MedicalRecord): Observable<MedicalRecord> {
        return apiMedicalRecordService.editMedicalRecord(medicalRecord.toMedicalRecordDataModel())
            .map { it.toMedicalRecord() }
    }
}