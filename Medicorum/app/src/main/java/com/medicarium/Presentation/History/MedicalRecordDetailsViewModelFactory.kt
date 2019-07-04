package com.medicarium.Presentation.History

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.ApiServices.MedicalRecordService
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Data.Models.MedicalRecordEntry

@Suppress("UNCHECKED_CAST")
class MedicalRecordDetailsViewModelFactory(
    private val application: Application,
    private val medicalRecordsViewModelFactory: MedicalRecordsViewModelFactory,
    private val medicalRecordService: MedicalRecordService
): ViewModelProvider.NewInstanceFactory() {
    var instance: MedicalRecordDetailsViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            // instance = MedicalRecordDetailsViewModel(application, medicalRecordsViewModelFactory.instance!!.lastSelectedMedicalRecord)
            instance = MedicalRecordDetailsViewModel(application,
                medicalRecordsViewModelFactory.instance!!.lastSelectedMedicalRecord,
                medicalRecordService,
                medicalRecordsViewModelFactory.instance!!)

        return instance as T
    }
}