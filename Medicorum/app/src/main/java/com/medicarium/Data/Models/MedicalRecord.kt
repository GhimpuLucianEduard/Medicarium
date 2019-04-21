package com.medicarium.Data.Models

import com.medicarium.Data.Enums.MedicalCategory
import empty
import java.util.*

data class MedicalRecord(
    var id: String = String.empty(),
    var name: String = String.empty(),
    var medicalCategory: MedicalCategory,
    var timestamp: Long,
    var entries: List<MedicalRecordEntry> = emptyList()
)