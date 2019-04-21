package com.medicarium.Data.DataModels

import com.google.gson.annotations.SerializedName
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Data.Models.MedicalRecordEntry
import empty
import java.util.*

data class MedicalRecordDataModel(
    @SerializedName("_id")
    var id: String = String.empty(),
    var name: String = String.empty(),
    @SerializedName("category")
    var medicalCategory: MedicalCategory = MedicalCategory.OTHER,
    var timestamp: Long = Calendar.getInstance().timeInMillis,
    var entries: List<MedicalRecordEntryDataModel> = emptyList()
)