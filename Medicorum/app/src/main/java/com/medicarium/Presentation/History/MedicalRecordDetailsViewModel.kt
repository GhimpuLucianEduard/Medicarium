package com.medicarium.Presentation.History

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.medicarium.Data.ApiServices.MedicalRecordService
import com.medicarium.Data.Models.MedicalRecord
import deepClone

class MedicalRecordDetailsViewModel(
    application: Application,
    medicalRecord: MedicalRecord
) : ViewModel() {


    var originalMedicalRecord: MedicalRecord = medicalRecord
    val cloneMedicalRecord = MutableLiveData<MedicalRecord>()

    init {
        cloneMedicalRecord.value = originalMedicalRecord.deepClone()
    }

}
