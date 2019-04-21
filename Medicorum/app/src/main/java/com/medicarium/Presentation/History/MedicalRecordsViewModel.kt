package com.medicarium.Presentation.History

import add
import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.medicarium.Data.ApiServices.MedicalRecordService
import com.medicarium.Data.Mappers.toMedicalRecord
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Presentation.BaseAndroidViewModel
import com.medicarium.Presentation.DataViewModels.MedicalRecordObservable
import com.medicarium.Presentation.DataViewModels.PropertyAwareMutableLiveData
import com.medicarium.Utilities.Event
import com.medicarium.Utilities.LiveDataDoubleTrigger
import com.medicarium.Utilities.LoggerConstants
import delete
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MedicalRecordsViewModel(
    application: Application,
    private val medicalRecordsService: MedicalRecordService
) : BaseAndroidViewModel(application) {

    private val _medicalRecords = MutableLiveData<List<MedicalRecord>>()
    val medicalRecords: LiveData<List<MedicalRecord>>
        get() = _medicalRecords

    val isBusy = MutableLiveData<Boolean>()
    val medicalRecordToAdd = PropertyAwareMutableLiveData<MedicalRecordObservable>()
    val navigateBack = MutableLiveData<Event<Boolean>>()

    val isAddNewRecordEnabled: LiveData<Boolean> =
        Transformations.map(LiveDataDoubleTrigger(medicalRecordToAdd, isBusy)) {
            it.first!!.name.isNotEmpty() &&
                    it.first!!.timestamp != null &&
                    it.first!!.medicalCategory != null &&
                    it.second!!.not()
        }

    init {

        isBusy.value = true
        _medicalRecords.value = ArrayList()
        medicalRecordToAdd.value = MedicalRecordObservable()

        medicalRecordsService.getMedicalRecord()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LoggerConstants.API_REQ, "GetMedicalRecords request succeeded: $it")
                _medicalRecords.value = it
            }, {
                Log.e(LoggerConstants.API_REQ, "GetMedicalRecords request failed: ${it.message}")
                isBusy.value = false
            }, {
                isBusy.value = false
            })
            .addTo(compositeDisposable)
    }

    fun addMedicalRecord() {
        isBusy.value = true
        medicalRecordsService.addMedicalRecord(medicalRecordToAdd.value!!.toMedicalRecord())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LoggerConstants.API_REQ, "AddMedicalRecord request succeeded: $it")
                _medicalRecords.add(it)
                navigateBack.value = Event(true)
            }, {
                Log.e(LoggerConstants.API_REQ, "AddMedicalRecord request failed: ${it.message}")
                isBusy.value = false
            }, {
                isBusy.value = false
            })
            .addTo(compositeDisposable)
    }

    fun deleteMedicalRecord(id: String) {
        isBusy.value = true
        medicalRecordsService.deleteMedicalRecord(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i(LoggerConstants.API_REQ, "DeleteMedicalRecord request succeeded: $it")
                val deletedItem = _medicalRecords.value!!.find { record -> record.id == id }
                _medicalRecords.delete(deletedItem!!)
            }, {
                Log.e(LoggerConstants.API_REQ, "DeleteMedicalRecord request failed: ${it.message}")
                isBusy.value = false
            }, {
                isBusy.value = false
            })
            .addTo(compositeDisposable)
    }
}
