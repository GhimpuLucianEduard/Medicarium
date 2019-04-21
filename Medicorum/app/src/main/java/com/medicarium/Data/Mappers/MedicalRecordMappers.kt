package com.medicarium.Data.Mappers

import com.medicarium.Data.DataModels.MedicalRecordDataModel
import com.medicarium.Data.DataModels.MedicalRecordEntryDataModel
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Data.Models.MedicalRecordEntry
import com.medicarium.Presentation.DataViewModels.MedicalRecordObservable

fun MedicalRecord.toMedicalRecordDataModel() = MedicalRecordDataModel (
    id = id,
    name = name,
    timestamp = timestamp,
    medicalCategory = medicalCategory,
    entries = entries.map {
        it.toMedicalRecordEntryDataModel()
    }.toList()
)

fun MedicalRecordDataModel.toMedicalRecord() = MedicalRecord (
    id = id,
    name = name,
    timestamp = timestamp,
    medicalCategory = medicalCategory,
    entries = entries.map {
        it.toMedicalRecordEntry()
    }.toList()
)


fun MedicalRecordEntry.toMedicalRecordEntryDataModel() = MedicalRecordEntryDataModel (
    id = id,
    name = name
)

fun MedicalRecordEntryDataModel.toMedicalRecordEntry() = MedicalRecordEntry (
    id = id,
    name = name
)

fun MedicalRecord.toMedicalRecordObservable() = MedicalRecordObservable (
    id = id,
    name = name,
    timestamp = timestamp,
    medicalCategory = medicalCategory
)

fun MedicalRecordObservable.toMedicalRecord() = MedicalRecord (
    name = name,
    timestamp = timestamp!!,
    medicalCategory = medicalCategory!!
)