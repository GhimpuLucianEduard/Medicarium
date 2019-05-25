package com.medicarium.Presentation.History

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.medicarium.Data.ApiServices.MedicalRecordService
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Data.Models.MedicalRecordEntry
import com.medicarium.Presentation.DataViewModels.MedicalRecordObservable
import com.medicarium.Presentation.DataViewModels.PropertyAwareMutableLiveData
import deepClone

class MedicalRecordDetailsViewModel(
    application: Application,
    medicalRecord: MedicalRecord,
    medicalRecordService: MedicalRecordService,
    private val medicalRecordsViewModel: MedicalRecordsViewModel
) : ViewModel() {

    var originalMedicalRecord: MedicalRecord = medicalRecord
    val cloneMedicalRecord = MutableLiveData<MedicalRecord>()
    var medicalRecordToEdit = PropertyAwareMutableLiveData<MedicalRecordObservable>()

    val isSaveButtonEnabled: LiveData<Boolean> =
        Transformations.map(medicalRecordToEdit) {
            it.name.isNotEmpty()
        }

    init {
        onViewCreated()
    }

    fun removeEntry(currentPosition: Int) {
        val updatedItems = cloneMedicalRecord.value!!.entries as ArrayList
        updatedItems.remove(cloneMedicalRecord.value!!.entries[currentPosition])
        cloneMedicalRecord.value!!.entries = updatedItems
        cloneMedicalRecord.value = cloneMedicalRecord.value!!.deepClone()
    }

    fun addNewEntry(entry: MedicalRecordEntry) {
        val updatedItems = cloneMedicalRecord.value!!.entries as ArrayList
        updatedItems.add(entry)
        cloneMedicalRecord.value!!.entries = updatedItems
        cloneMedicalRecord.value = cloneMedicalRecord.value!!.deepClone()
    }

    fun deleteMedicalRecord() {
        medicalRecordsViewModel.deleteMedicalRecord(originalMedicalRecord.id)
    }

    fun saveChanges() {
        medicalRecordsViewModel.editMedicalRecord(cloneMedicalRecord.value!!)
    }

    fun onViewCreated() {
        cloneMedicalRecord.value = originalMedicalRecord.deepClone()
    }
}
