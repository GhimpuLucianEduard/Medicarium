package com.medicarium.Presentation.History

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medicarium.Data.ApiServices.MedicalRecordService

@Suppress("UNCHECKED_CAST")
class MedicalRecordsViewModelFactory(
    private val application: Application,
    private val medicalRecordsService: MedicalRecordService
): ViewModelProvider.NewInstanceFactory() {
    var instance: MedicalRecordsViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            instance = MedicalRecordsViewModel(application, medicalRecordsService)
        }
        return instance as T
    }
}