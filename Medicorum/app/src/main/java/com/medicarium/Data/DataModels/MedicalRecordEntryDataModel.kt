package com.medicarium.Data.DataModels

import com.google.gson.annotations.SerializedName
import empty

class MedicalRecordEntryDataModel(
    @SerializedName("_id")
    var id: String = String.empty(),
    var name: String = String.empty()
)