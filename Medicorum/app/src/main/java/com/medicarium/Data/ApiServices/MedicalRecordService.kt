package com.medicarium.Data.ApiServices

import com.medicarium.Data.Models.MedicalRecord
import io.reactivex.Observable

interface MedicalRecordService {
    fun addMedicalRecord(medicalRecord: MedicalRecord) : Observable<MedicalRecord>
    fun getMedicalRecord() : Observable<List<MedicalRecord>>
    fun deleteMedicalRecord(id: String) : Observable<Any>
    fun editMedicalRecord(medicalRecord: MedicalRecord): Observable<MedicalRecord>
}