package com.medicarium.Presentation.DataViewModels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.medicarium.BR
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Data.Models.MedicalRecordEntry
import empty
import java.util.*

class MedicalRecordObservable(
    id: String = String.empty(),
    name: String = String.empty(),
    medicalCategory: MedicalCategory? = null,
    timestamp: Long? = null
): BaseObservable() {

    @Bindable
    var name: String = name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @Bindable
    var medicalCategory: MedicalCategory? = medicalCategory
        set(value) {
            field = value
            notifyPropertyChanged(BR.medicalCategory)
        }

    @Bindable
    var timestamp: Long? = timestamp
        set(value) {
            field = value
            notifyPropertyChanged(BR.timestamp)
        }
}