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
    private val medicalRecordsViewModelFactory: MedicalRecordsViewModelFactory
): ViewModelProvider.NewInstanceFactory() {
    var instance: MedicalRecordDetailsViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (instance == null) {
            // instance = MedicalRecordDetailsViewModel(application, medicalRecordsViewModelFactory.instance!!.lastSelectedMedicalRecord)
            instance = MedicalRecordDetailsViewModel(application, MedicalRecord(
                id = "dsadsadsada",
                name = "Analiza de sange",
                timestamp = 1558195422000,
                medicalCategory = MedicalCategory.OTOLOGY,
                entries = listOf(
                    MedicalRecordEntry("das", "Poza 1", "https://picsum.photos/id/237/1920/1080"),
                    MedicalRecordEntry("das", "Poza 2", "https://picsum.photos/id/197/1920/1080"),
                    MedicalRecordEntry("das", "Poza 3", "https://picsum.photos/id/107/1920/1080"),
                    MedicalRecordEntry("das", "Poza 4", "https://picsum.photos/id/257/1920/1080")
                )

            ))
        }
        return instance as T
    }
}